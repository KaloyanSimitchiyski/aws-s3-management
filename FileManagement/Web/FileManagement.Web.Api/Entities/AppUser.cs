using System;
using System.ComponentModel.DataAnnotations;

using FileManagement.Web.Api.Common;
using Microsoft.AspNetCore.Identity;

namespace FileManagement.Web.Api.Entities
{
    public class AppUser : IdentityUser
    {
        public AppUser()
        {
            this.Folder = Guid.NewGuid();
        }

        [Required]
        [MinLength(GlobalConstants.MinNameLength)]
        [MaxLength(GlobalConstants.MaxNameLength)]
        public string FirstName { get; set; }

        [Required]
        [MinLength(GlobalConstants.MinNameLength)]
        [MaxLength(GlobalConstants.MaxNameLength)]
        public string LastName { get; set; }

        public Guid Folder { get; set; }
    }
}
