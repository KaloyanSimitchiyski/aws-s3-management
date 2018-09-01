using FileManagement.Data.Models;
using Microsoft.AspNet.Identity.EntityFramework;

namespace FileManagement.Data
{
    public class FileManagementDbContext : IdentityDbContext<User>
    {
        public FileManagementDbContext()
            : base("DefaultConnection", throwIfV1Schema: false)
        {
        }

        public static FileManagementDbContext Create()
        {
            return new FileManagementDbContext();
        }
    }
}
