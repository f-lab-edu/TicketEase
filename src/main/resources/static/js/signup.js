const usernameInput = document.getElementById('usernameInput');
const usernameLengthMessage = document.getElementById('usernameLengthMessage');
const passwordInput = document.getElementById('passwordInput');
const passwordRequirements = document.getElementById('passwordRequirements');
const confirmPasswordInput = document.getElementById('confirmPasswordInput');
const passwordMismatchMessage = document.getElementById('passwordMismatch');
const registerButton = document.getElementById('registerButton');

usernameInput.addEventListener('input', function() {
    if (usernameInput.value.length >= 5) {
        usernameLengthMessage.style.display = 'none'; // Hide the message
        usernameInput.classList.remove('is-invalid'); // Remove any invalid style
    } else {
        usernameLengthMessage.style.display = 'block'; // Show the message
        usernameInput.classList.add('is-invalid'); // Add invalid style
    }
    updateRegisterButtonState();
});

passwordInput.addEventListener('input', function() {
    if (passwordInput.value.length >= 8 && /\d/.test(passwordInput.value) && /[a-zA-Z]/.test(passwordInput.value)) {
        passwordRequirements.style.display = 'none'; // Hide the message
        passwordInput.classList.remove('is-invalid'); // Remove any invalid style
    } else {
        passwordRequirements.style.display = 'block'; // Show the message
        passwordInput.classList.add('is-invalid'); // Add invalid style
    }
    validatePasswords(); // Check password match after each input
    updateRegisterButtonState();
});

confirmPasswordInput.addEventListener('input', function() {
    validatePasswords(); // Check password match after each input
    updateRegisterButtonState();
});

function validatePasswords() {
    if (confirmPasswordInput.value.length === 0) {
        passwordMismatchMessage.style.display = 'none'; // Hide the message
        confirmPasswordInput.classList.remove('is-invalid'); // Remove any invalid style
    } else if (passwordInput.value === confirmPasswordInput.value) {
        passwordMismatchMessage.style.display = 'none'; // Hide the message
        confirmPasswordInput.classList.remove('is-invalid'); // Remove any invalid style
    } else {
        passwordMismatchMessage.style.display = 'block'; // Show the message
        confirmPasswordInput.classList.add('is-invalid'); // Add invalid style
    }
}

function updateRegisterButtonState() {
    if (usernameInput.value.length >= 5 &&
        passwordInput.value.length >= 8 && /\d/.test(passwordInput.value) && /[a-zA-Z]/.test(passwordInput.value) &&
        passwordInput.value === confirmPasswordInput.value) {
        registerButton.removeAttribute('disabled');
    } else {
        registerButton.setAttribute('disabled', 'disabled');
    }
}