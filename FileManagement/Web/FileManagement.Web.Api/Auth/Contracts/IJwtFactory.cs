using System.Security.Claims;
using System.Threading.Tasks;

namespace FileManagement.Web.Api.Auth.Contracts
{
    public interface IJwtFactory
    {
        Task<string> GenerateEncodedToken(string userName, ClaimsIdentity identity);

        ClaimsIdentity GenerateClaimsIdentity(string userName, string id);
    }
}
