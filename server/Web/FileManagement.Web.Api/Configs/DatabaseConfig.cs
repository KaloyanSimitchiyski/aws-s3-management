using System.Data.Entity;

using FileManagement.Data;
using FileManagement.Data.Migrations;

namespace FileManagement.Web.Api.Configs
{
    public static class DatabaseConfig
    {
        public static void Initialize()
        {
            Database.SetInitializer(new MigrateDatabaseToLatestVersion<FileManagementDbContext, Configuration>());
        }
    }
}
