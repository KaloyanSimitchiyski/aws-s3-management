using FileManagement.Common;
using FluentValidation;

namespace FileManagement.ViewModels.Validations
{
    public class RegistrationViewModelValidator : AbstractValidator<RegistrationViewModel>
    {
        public RegistrationViewModelValidator()
        {
            RuleFor(vm => vm.Email)
                .NotEmpty()
                .EmailAddress()
                .WithMessage(GlobalConstants.EmailErrorMessage);

            RuleFor(vm => vm.Password)
                .NotEmpty()
                .WithMessage(GlobalConstants.PasswordErrorMessage);

            RuleFor(vm => vm.FirstName)
                .Length(GlobalConstants.MinNameLength, GlobalConstants.MaxNameLength)
                .WithMessage(GlobalConstants.NameErrorMessage);

            RuleFor(vm => vm.LastName)
                .Length(GlobalConstants.MinNameLength, GlobalConstants.MaxNameLength)
                .WithMessage(GlobalConstants.NameErrorMessage);
        }
    }
}
