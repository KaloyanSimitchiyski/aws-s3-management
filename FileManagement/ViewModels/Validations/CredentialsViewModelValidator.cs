using FileManagement.Common;
using FluentValidation;

namespace FileManagement.ViewModels.Validations
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
