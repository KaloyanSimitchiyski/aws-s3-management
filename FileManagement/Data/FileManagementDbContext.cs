using FileManagement.Entities;

using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;

namespace FileManagement.Data
{
    public class FileManagementDbContext : IdentityDbContext<AppUser>
    {
        public FileManagementDbContext(DbContextOptions options)
            : base(options)
        {
            
        }
    }
}
