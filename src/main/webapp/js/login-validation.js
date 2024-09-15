$(document).ready(function () {
    // Initialize popovers for fields
    $('[data-bs-toggle="popover"]').popover({
        placement: 'right',
        trigger: 'manual'
    });

// Email validation function
function validateEmail() {
    var email = $("#emailInput").val();
    var emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    var isValid = true;

    // Clear previous validation states
    $("#emailInput").removeClass("is-invalid is-valid");
    $("#emailHelp").css("visibility", "hidden");

    // Check email format
    if (!emailRegex.test(email)) {
        $("#emailInput").addClass("is-invalid");
        $("#emailHelp").text("Invalid email format").css({"color": "red", "visibility": "visible"});
        isValid = false;
    } else {
        $("#emailInput").addClass("is-valid");
        $("#emailHelp").text("Correct email format").css({"color": "green", "visibility": "visible"});
    }

    return isValid;
}

    function validatePassword() {
        var isValid = true;
        var passwordField = $('#passwordInput');
        var passwordValue = passwordField.val();
        var passwordHelp = $('#passwordHelp');

        // Clear previous validation states
        passwordField.removeClass('is-invalid is-valid');
        passwordHelp.css('visibility', 'hidden');

        // Validate password length
        if (passwordValue.length < 6) {
            passwordField.addClass('is-invalid');
            passwordHelp.text('Password must be at least 6 characters long.').css('visibility', 'visible');
            isValid = false;
        } else {
            passwordField.addClass('is-valid');
        }

        return isValid;
    }

    // Array of validation functions
    const validations = [validateEmail,validatePassword];

        $('#emailInput').on('input blur', function () {
            validateEmail();
        });
    $('#passwordInput').on('input blur', function () {
        validatePassword();
    });


    // Validate on form submit
    $('#buyerLoginForm').on('submit', function (event) {
        let isValid = true;

        // Loop through all validations
        validations.forEach(function (validate) {
            if (!validate()) {
                isValid = false;
            }
        });

        // Prevent form submission if any validation fails
        if (!isValid) {
            event.preventDefault();
        }
    });
});
