using System.Web;
using System.Web.Http;

using FileManagement.Web.Api.Configs;

namespace FileManagement.Web.Api
{
    public class WebApiApplication : HttpApplication
    {
        protected void Application_Start()
        {
            DatabaseConfig.Initialize();
            GlobalConfiguration.Configure(WebApiConfig.Register);
        }
    }
}
