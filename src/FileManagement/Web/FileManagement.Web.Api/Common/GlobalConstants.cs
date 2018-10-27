namespace FileManagement.Web.Api.Common
{
    public static class GlobalConstants
    {
        public const string ConnectionStringKey = "DefaultConnection";
        public const string MigrationsAssembly = "FileManagement.Web.Api";

        // User constants
        public const string AccountCreatedMessage = "Account created";
        public const string EmailErrorMessage = "The passed email is not valid";
        public const string PasswordErrorMessage = "Password cannot be empty";
        public const string NameErrorMessage = "The passed name is not valid";

        public const int MinNameLength = 2;
        public const int MaxNameLength = 30;
        public const int PasswordRequiredLength = 8;
    }
}
