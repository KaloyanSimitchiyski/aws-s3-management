namespace FileManagement.Web.Api.Common
{
    public static class GlobalConstants
    {
        public const string ConnectionStringKey = "DefaultConnection";
        public const string MigrationsAssembly = "FileManagement.Web.Api";

        // User constants
        public const int MinNameLength = 2;
        public const int MaxNameLength = 30;
    }
}
