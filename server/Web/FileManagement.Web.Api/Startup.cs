using System.Web.Http;
using FileManagement.Web.Api.Configs;
using Microsoft.Owin;
using Owin;

[assembly: OwinStartup(typeof(FileManagement.Web.Api.Startup))]

namespace FileManagement.Web.Api
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);

            var httpConfig = new HttpConfiguration();
            WebApiConfig.Register(httpConfig);
            httpConfig.EnsureInitialized();
        }
    }
}
