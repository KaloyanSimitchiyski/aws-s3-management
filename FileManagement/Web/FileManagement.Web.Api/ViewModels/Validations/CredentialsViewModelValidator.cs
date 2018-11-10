using FileManagement.Web.Api.Common;
using FluentValidation;

namespace FileManagement.Web.Api.ViewModels.Validations
{
    public class CredentialsViewModelValidator : AbstractValidator<CredentialsViewModel>
    {
        public CredentialsViewModelValidator()
        {
            RuleFor(vm => vm.Email)
                .NotEmpty()
                .EmailAddress()
                .WithMessage(GlobalConstants.EmailErrorMessage);

            RuleFor(vm => vm.Password)
                .MinimumLength(GlobalConstants.PasswordRequiredLength)
                .WithMessage(GlobalConstants.PasswordLengthErrorMessage);
        }
    }
}
