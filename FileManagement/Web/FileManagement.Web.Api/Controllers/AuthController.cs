using System;
using System.Security.Claims;
using System.Threading.Tasks;

using FileManagement.Web.Api.Auth.Contracts;
using FileManagement.Web.Api.Common;
using FileManagement.Web.Api.Entities;
using FileManagement.Web.Api.Models;
using FileManagement.Web.Api.ViewModels;

using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;

using Newtonsoft.Json;

namespace FileManagement.Web.Api.Controllers
{
    [Route("api/[controller]")]
    public class AuthController : Controller
    {
        private readonly UserManager<AppUser> _userManager;
        private readonly IJwtFactory _jwtFactory;
        private readonly JwtIssuerOptions _jwtOptions;

        public AuthController(UserManager<AppUser> userManager, IJwtFactory jwtFactory, IOptions<JwtIssuerOptions> jwtOptions)
        {
            this._userManager = userManager ?? throw new ArgumentNullException(nameof(userManager));
            this._jwtFactory = jwtFactory ?? throw new ArgumentNullException(nameof(jwtFactory));
            this._jwtOptions = jwtOptions.Value;
        }

        // POST api/auth/login
        [HttpPost("login")]
        public async Task<IActionResult> Post([FromBody]CredentialsViewModel credentials)
        {
            if (!this.ModelState.IsValid) return this.BadRequest(this.ModelState);

            var user = await this.GetUser(credentials.Email);
            var identity = await this.GetClaimsIdentity(user, credentials.Email, credentials.Password);
            if (identity == null)
                return this.BadRequest(Errors.AddErrorToModelState("login_failure", "Invalid username or password.", ModelState));

            var folder = user.Folder;
            var jsonSerializerSettings = new JsonSerializerSettings { Formatting = Formatting.Indented };
            var jwt = await Tokens.GenerateJwt(identity, this._jwtFactory, credentials.Email, folder, this._jwtOptions, jsonSerializerSettings);

            return new OkObjectResult(jwt);
        }

        private async Task<AppUser> GetUser(string username)
        {
            if (string.IsNullOrEmpty(username))
                return await Task.FromResult<AppUser>(null);

            return await this._userManager.FindByNameAsync(username);
        }

        private async Task<ClaimsIdentity> GetClaimsIdentity(AppUser userToVerify, string username, string password)
        {
            if (string.IsNullOrEmpty(password))
                return await Task.FromResult<ClaimsIdentity>(null);

            if (userToVerify == null) return await Task.FromResult<ClaimsIdentity>(null);

            // check the credentials
            if (await this._userManager.CheckPasswordAsync(userToVerify, password))
                return await Task.FromResult(this._jwtFactory.GenerateClaimsIdentity(username, userToVerify.Id));

            // Credentials are invalid, or account doesn't exist
            return await Task.FromResult<ClaimsIdentity>(null);
        }
    }
}
