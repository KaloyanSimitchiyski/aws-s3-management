using System;
using System.Threading.Tasks;

using AutoMapper;

using FileManagement.Web.Api.Common;
using FileManagement.Web.Api.Entities;
using FileManagement.Web.Api.ViewModels;

using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;

namespace FileManagement.Web.Api.Controllers
{
    [Route("api/[controller]")]
    public class AccountsController : Controller
    {
        private readonly UserManager<AppUser> _userManager;
        private readonly IMapper _mapper;

        public AccountsController(UserManager<AppUser> userManager, IMapper mapper)
        {
            this._userManager = userManager ?? throw new ArgumentNullException(nameof(userManager));
            this._mapper = mapper ?? throw new ArgumentNullException(nameof(mapper));
        }

        // POST api/accounts
        [HttpPost]
        public async Task<IActionResult> Post([FromBody]RegistrationViewModel viewModel)
        {
            if (!this.ModelState.IsValid) return this.BadRequest(this.ModelState);

            var userIdentity = this._mapper.Map<AppUser>(viewModel);
            var result = await this._userManager.CreateAsync(userIdentity, viewModel.Password);

            if (!result.Succeeded) return new BadRequestObjectResult(Errors.AddErrorsToModelState(result, this.ModelState));

            return new OkObjectResult(GlobalConstants.AccountCreatedMessage);
        }
    }
}
