$(document).ready(function () {
    //--- Validate Price
    function validatePrice() {


        let isValid = true;
        let priceField = $('#priceInput');
        let priceValue = priceField.val();
        let priceHelp = $('#priceHelp');

        // Clear previous validation states
        priceField.removeClass('is-invalid is-valid');
        priceHelp.css('visibility', 'hidden');

        // Validate credit limit format and value
        const priceRegex = /^(?!0$)\d+(\.\d{1,2})?$/;
        if (!priceRegex.test(priceValue) || parseFloat(priceValue) < 0) {
            priceField.addClass('is-invalid');
            priceHelp.text('Price must be a non-negative number.').css('visibility', 'visible');
            isValid = false;
        } else {
            priceField.addClass('is-valid');
        }
        return isValid;
    }
    //-- Validate Quantity
    function validateQuantity() {
        let isValid = true;
        let quantityField = $('#quantityInput');
        let quantityValue = quantityField.val();
        let quantityHelp = $('#quantityHelp');

        // Clear previous validation states
        quantityField.removeClass('is-invalid is-valid');
        quantityHelp.css('visibility', 'hidden');

        // Validate credit limit format and value
        const quantityRegex = /^[1-9]\d*$/;
        if (!quantityRegex.test(quantityValue) || parseInt(quantityValue) < 0) {
            quantityField.addClass('is-invalid');
            quantityHelp.text('Quantity must be a non-negative number.').css('visibility', 'visible');
            isValid = false;
        } else {
            quantityField.addClass('is-valid');
        }
        return isValid;
    }
    //-- ISBN Validation  
    function validateISBN() {
        console.log("Wasalna el validation el 3ady");
        let isbn = $("#isbnInput").val().trim();
        let isValid = true;

        // Clear previous validation states
        $("#isbnInput").removeClass("is-invalid is-valid");
        $("#isbnHelp").css({ "visibility": "hidden", "color": "" });

        // Regular expressions for ISBN-10 and ISBN-13
        let isbn10Regex = /^\d{9}[\dX]$/;  // 9 digits followed by a digit or 'X'
        let isbn13Regex = /^\d{13}$/;      // Exactly 13 digits

        // Check ISBN length and format
        if (!isbn10Regex.test(isbn) && !isbn13Regex.test(isbn)) {
            $("#isbnInput").addClass("is-invalid");
            $("#isbnHelp").text("Invalid ISBN format. Must be 10 or 13 digits long.").css({ "visibility": "visible", "color": "red" });
            isValid = false;
        }
        return isValid;
    }

    function validateISBNWithAjax() {
        const deferred = $.Deferred();

        if (validateISBN()) {

            $.ajax({
                url: "/unique-isbn",
                method: "POST",
                data: { isbn: $("#isbnInput").val() },
                success: function (response) {
                    if (response === "true") {
                        $("#isbnInput").removeClass("is-invalid").addClass("is-valid");
                        $("#isbnHelp").text("ISBN available").css({
                            "visibility": "visible", "color": "green"
                        });
                        deferred.resolve(true); // Resolve when phone number is valid
                    } else if (response === "ISBN already exists") {
                        $("#isbnInput").addClass("is-invalid").removeClass("is-valid");
                        $("#isbnHelp").text("ISBN already exists").css({
                            "visibility": "visible", "color": "red"
                        });
                        deferred.resolve(false); // Resolve when phone number is invalid
                    } else {
                        $("#isbnInput").addClass("is-invalid").removeClass("is-valid");
                        $("#isbnHelp").text("ISBN already exists").css({
                            "visibility": "visible", "color": "red"
                        });
                        deferred.resolve(false); // Resolve when there's an error
                    }
                },
                error: function (error) {
                    console.error("Error checking ISBN:", error);
                    $("#isbnInput").addClass("is-invalid").removeClass("is-valid");
                    $("#isbnHelp").text("ISBN already exists").css({
                        "visibility": "visible", "color": "red"
                    });
                    deferred.resolve(false); // Resolve when there's an error
                }
            });
        } else {
            deferred.resolve(false); // Resolve immediately if format is invalid
        }

        return deferred.promise();
    }
    function validateName() {
        let isValid = true;
        let nameField = $('#nameInput');
        let nameValue = nameField.val().trim();
        let nameHelp = $('#nameHelp');

        // Clear previous validation states
        nameField.removeClass('is-invalid is-valid');
        nameField.popover('hide');
        nameHelp.css('visibility', 'hidden');

        // Validate that the field is not empty and does not exceed 100 characters
        if (nameValue === '') {
            nameField.addClass('is-invalid');
            nameField.attr('data-bs-content', 'Name cannot be empty.');
            nameField.popover('show');
            nameHelp.text('Name cannot be empty.').css('visibility', 'visible');
            isValid = false;
        } else if (nameValue.length > 100) {
            nameField.addClass('is-invalid');
            nameField.attr('data-bs-content', 'Name cannot exceed 100 characters.');
            nameField.popover('show');
            nameHelp.text('Name should not exceed 100 characters.').css('visibility', 'visible');
            isValid = false;
        } else {
            // Input is valid
            nameField.addClass('is-valid');
            nameHelp.css('visibility', 'hidden');
        }
        return isValid;
    }
    function validateAuthor() {
        let isValid = true;
        let authorField = $('#authorInput');
        let authorValue = authorField.val().trim();
        let authorHelp = $('#authorHelp');

        // Clear previous validation states
        authorField.removeClass('is-invalid is-valid');
        authorField.popover('hide');
        authorHelp.css('visibility', 'hidden');

        // Validate that the field is not empty and does not exceed 100 characters
        if (authorValue === '') {
            authorField.addClass('is-invalid');
            authorField.attr('data-bs-content', 'author cannot be empty.');
            authorField.popover('show');
            authorHelp.text('Author Name cannot be empty.').css('visibility', 'visible');
            isValid = false;
        } else if (authorValue.length > 100) {
            authorField.addClass('is-invalid');
            authorField.attr('data-bs-content', 'Author Name cannot exceed 100 characters.');
            authorField.popover('show');
            authorHelp.text('Author Name should not exceed 100 characters.').css('visibility', 'visible');
            isValid = false;
        } else {
            // Input is valid
            authorField.addClass('is-valid');
            authorHelp.css('visibility', 'hidden');
        }
        return isValid;
    }
    function validateDescription() {
        let isValid = true;
        let descriptionField = $('#descriptionInput');
        let descriptionValue = descriptionField.val().trim();
        let descriptionHelp = $('#descriptionHelp');

        // Clear previous validation states
        descriptionField.removeClass('is-invalid is-valid');
        descriptionField.popover('hide');
        descriptionHelp.css('visibility', 'hidden');

        // Validate that the field is not empty and does not exceed 100 characters
        if (descriptionValue === '') {
            descriptionField.addClass('is-invalid');
            descriptionField.attr('data-bs-content', 'Description cannot be empty.');
            descriptionField.popover('show');
            descriptionHelp.text('Description cannot be empty.').css('visibility', 'visible');
            isValid = false;
        } else if (descriptionValue.length > 500) {
            descriptionField.addClass('is-invalid');
            descriptionField.attr('data-bs-content', 'description Name cannot exceed 500 characters.');
            descriptionField.popover('show');
            descriptionHelp.text('Description should not exceed 500 characters.').css('visibility', 'visible');
            isValid = false;
        } else {
            // Input is valid
            descriptionField.addClass('is-valid');
            descriptionHelp.css('visibility', 'hidden');
        }
        return isValid;
    }
    function validateReleaseDate() {
        let releaseDate = $("#releaseDate").val().trim();
        let isValid = true;

        // Clear previous validation states
        $("#releaseDate").removeClass("is-invalid is-valid");
        $("#releaseDateHelp").css({ "visibility": "hidden", "color": "" });

        // Check if release date is empty
        if (releaseDate === "") {
            $("#releaseDate").addClass("is-invalid");
            $("#releaseDateHelp").text("Release Date must not be empty").css({ "visibility": "visible", "color": "red" });
            isValid = false;
        } else {
            $("#releaseDate").addClass("is-valid");
        }

        return isValid;
    }
    function validateCategory() {
        let categoryId = $("#categoryId").val();
        let isValid = true;

        // Clear previous validation states
        $("#categoryId").removeClass("is-invalid is-valid");
        $("#categoryHelp").css({ "visibility": "hidden", "color": "" });

        // Check if a category is selected
        if (!categoryId) {
            $("#categoryId").addClass("is-invalid");
            $("#categoryHelp").text("At least one category should be selected.")
                .css({ "visibility": "visible", "color": "red" });
            isValid = false;
        } else {
            $("#categoryId").addClass("is-valid");
        }

        return isValid;
    }

    function validateImagePath() {
        let imagePath = $("#imagePath").val();
        let isValid = true;

        // Clear previous validation states
        $("#imagePath").removeClass("is-invalid is-valid");
        $("#imagePathHelp").css({ "visibility": "hidden", "color": "" });

        // Check if a file is selected
        if (!imagePath) {
            $("#imagePath").addClass("is-invalid");
            $("#imagePathHelp").text("You must upload an image.")
                .css({ "visibility": "visible", "color": "red" });
            isValid = false;
        } else {
            $("#imagePath").addClass("is-valid");
        }

        return isValid;
    }

    //Array of validations
    const validations = [];
    validations.push(validatePrice);
    validations.push(validateQuantity);
    validations.push(validateISBNWithAjax);
    validations.push(validateName);
    validations.push(validateAuthor);
    validations.push(validateDescription);
    validations.push(validateReleaseDate);
    validations.push(validateCategory);
    validations.push(validateImagePath);



    //Listeners
    $('#priceInput').on('input', function () {
        validatePrice();
    });
    //Listeners
    $('#quantityInput').on('input', function () {
        validateQuantity();
    });
    $('#releaseDateInput').on('input', function () {
        validateReleaseDate();
    });
    $('#isbnInput').on('blur', function () {
        validateISBNWithAjax();
    });
    $('#nameInput').on('input', function () {
        validateName();
    });
    $('#authorInput').on('input', function () {
        validateAuthor();
    });
    $('#descriptionInput').on('input', function () {
        validateDescription();
    });
    $("#releaseDate").on('input', function () {
        validateReleaseDate();
    });
    $("select[name='categoryId']").on('input', function () {
        validateCategory();
    });
    $("#imagePath").on('change', function () {
        validateImagePath();
    });
    //Button Prevention 
    $('#add-book').on('submit', function (event) {
        event.preventDefault(); // Prevent default submission until validation completes
        let isValid = true;

        // Validate all fields except email and phone first
        validations.forEach(function (validate) {
            if (validate !== validateISBNWithAjax && !validate()) {
                isValid = false;
            }
        });

        // Wait for the email validation via AJAX
        $.when(validateISBNWithAjax()).then(function (emailValid, phoneValid) {
            if (isValid && emailValid && phoneValid) {
                // All validations passed, proceed with form submission
                $('#add-book').off('submit').submit(); // Unbind and trigger form submit
            }
        });
    });
});
