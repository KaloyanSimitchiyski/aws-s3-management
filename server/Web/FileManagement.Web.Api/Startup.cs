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
        }
    }
}
