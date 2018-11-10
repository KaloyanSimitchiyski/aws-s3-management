using FileManagement.Web.Api.Entities;

using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;

namespace FileManagement.Web.Api.Data
{
    public class FileManagementDbContext : IdentityDbContext<AppUser>
    {
        public FileManagementDbContext(DbContextOptions options)
            : base(options)
        {
            
        }
    }
}
