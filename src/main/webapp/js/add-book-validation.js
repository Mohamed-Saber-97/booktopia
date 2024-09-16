$(document).ready(function () {
    // Initialize popovers for fields
    $('[data-bs-toggle="popover"]').popover({
        placement: 'right',
        trigger: 'manual'
    });

    window.onload = document.title = 'Add a Book'

    // Credit limit validation function
    function validatePrice() {
        var isValid = true;
        var price = $('#price');
        var priceValue = price.val();
        var priceHelp = $('#priceHelp');

        // Clear previous validation states
        price.removeClass('is-invalid is-valid');
        priceHelp.css('visibility', 'hidden');

        // Validate credit limit format and value
        var priceRegex = /^\d+(\.\d+)?$/;
        if (!priceRegex.test(priceValue) || parseFloat(priceValue) < 0) {
            price.addClass('is-invalid');
            priceHelp.text('Price must be a valid non-negative amount.').css('visibility', 'visible');
            isValid = false;
        } else {
            price.addClass('is-valid');
        }

        return isValid;
    }

    // Credit limit validation function
    function validateQuantity() {
        var isValid = true;
        var qty = $('#qty');
        var qtyValue = qty.val();
        var qtyHelp = $('#qtyHelp');

        // Clear previous validation states
        qty.removeClass('is-invalid is-valid');
        qtyHelp.css('visibility', 'hidden');

        var qtyRegex = /^(0|[1-9][0-9]*)$/;
        if (!qtyRegex.test(qtyValue) || parseInt(qtyValue) < 0) {
            qty.addClass('is-invalid');
            qtyHelp.text('Quantity must be a valid, non-negative, whole amount.').css('visibility', 'visible');
            isValid = false;
        } else {
            qty.addClass('is-valid');
        }

        return isValid;
    }

    function validateReleaseDate() {
        let isValid = true;
        let rdField = $('#releaseDate');
        let rdValue = rdField.val();
        let rdHelp = $('#rdHelp');

        // Clear previous validation states
        rdField.removeClass('is-invalid is-valid');
        rdField.popover('hide');
        rdHelp.css('visibility', 'hidden');

        // Validate the date
        if (!rdValue) {
            rdField.addClass('is-invalid');
            rdField.attr('data-bs-content', 'Date of release cannot be empty.');
            rdField.popover('show');
            rdHelp.text('Date of release cannot be empty.').css('visibility', 'visible');
            isValid = false;
        } else {
            let rd = new Date(rdValue);
            let today = new Date();
            const monthDifference = (today.getFullYear() - red.getFullYear()) * 12 + (today.getMonth - rd.getMonth);


            console.log(monthDifference);

            if (monthDifference < 2) {
                rdField.addClass('is-invalid');
                rdField.attr('data-bs-content', 'A book must be published at least two months ago.');
                rdField.popover('show');
                rdHelp.text('A book must be published at least two months ago.').css('visibility', 'visible');
                isValid = false;
            } else {
                // Input is valid
                rdField.addClass('is-valid');
                rdHelp.css('visibility', 'hidden');
            }
        }

        return isValid;
    }

    function validateISBN() {
        var isbn = $("#isbn").val();
        var isbnRegex = /^(?:\d{1,5}-\d{1,7}-\d{1,6}-[\dX]|\d{1,5}-\d{1,7}-\d{1,6}-\d{1,6}-\d{1})$/;
        var isValid = true;

        // Clear previous validation states
        $("#isbn").removeClass("is-invalid is-valid");
        $("#isbnHelp").css("visibility", "hidden");

        if (!isbnRegex.test(isbn)) {
            $("#isbn").addClass("is-invalid");
            $("#isbnHelp").text("Invalid ISBN format").css("visibility", "visible");
            isValid = false;
        }

        return isValid;
    }

    function validateAuthor() {
        let isValid = true;
        let author = $('#author');
        let authorValue = author.val().trim();
        let authorHelp = $('#authorHelp');

        // Clear previous validation states
        author.removeClass('is-invalid is-valid');
        author.popover('hide');
        authorHelp.css('visibility', 'hidden');

        // Validate that the field is not empty and does not exceed 100 characters
        if (!authorValue || authorValue === '') {
            author.addClass('is-invalid');
            author.attr('data-bs-content', 'Author cannot be empty.');
            author.popover('show');
            authorHelp.text('Author cannot be empty.').css('visibility', 'visible');
            isValid = false;
        } else if (authorValue.length > 100) {
            author.addClass('is-invalid');
            author.attr('data-bs-content', 'Author cannot exceed 100 characters.');
            author.popover('show');
            authorHelp.text('Author should not exceed 100 characters.').css('visibility', 'visible');
            isValid = false;
        } else {
            // Input is valid
            author.addClass('is-valid');
            authorHelp.css('visibility', 'hidden');
        }

        return isValid;
    }

    // Name field validation
    function validateName() {
        let isValid = true;
        let nameField = $('#name');
        let nameValue = nameField.val().trim();
        let nameHelp = $('#nameHelp');

        // Clear previous validation states
        nameField.removeClass('is-invalid is-valid');
        nameField.popover('hide');
        nameHelp.css('visibility', 'hidden');

        // Validate that the field is not empty and does not exceed 100 characters
        if (!nameValue || nameValue === '') {
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

    function validateDescription() {
        var isValid = true;
        var description = $('#desc');
        var descHelp = $('#descHelp');

        // Clear previous validation states
        description.removeClass('is-invalid is-valid');  // Remove previous states
        description.popover('hide');
        descHelp.css('visibility', 'hidden');

        if (description.val() === '' || description.val() === null) {
            description.addClass('is-invalid');  // Add invalid state
            description.attr('data-bs-content', 'Please Enter a description.');
            description.popover('show');
            descHelp.text('Please Enter a description.').css('visibility', 'visible');
            isValid = false;
        } else {
            description.addClass('is-valid');  // Add valid state (green border)
            descHelp.css('visibility', 'hidden');
        }

        return isValid;
    }

    function validateCategories() {
        var isValid = true;
        var categoriesField = $('.interest-bubble input[type="checkbox"]');
        var categoriesHelp = $('#categoriesHelp');

        // Clear previous validation states
        categoriesHelp.css('visibility', 'hidden');

        // Check if at least one category is selected
        if (!categoriesField.is(':checked')) {
            categoriesHelp.text('Please select at least one category.').css('visibility', 'visible');
            isValid = false;
        } else {
            // All fields are valid
            categoriesHelp.css('visibility', 'hidden');
        }

        return isValid;
    }



    // Array of validation functions
    const validations = [];
    validations.push(validateName);
    validations.push(validateReleaseDate);
    validations.push(validateAuthor);
    validations.push(validateISBN);
    validations.push(validatePrice);
    validations.push(validateQuantity);
    validations.push(validateDescription);
    validations.push(validateCategories);



    // Validate each input field on change
    $('#name').on('blur', function () {
        validateName();
    });
    $('#releaseDate').on('blur', function () {
        validateReleaseDate();
    });
    $('#author').on('blur', function () {
        validateAuthor();
    });
    $('#isbn').on('blur', function () {
        validateISBN();

        // Proceed with AJAX if format is valid
        if (validateISBN()) {
            $.ajax({
                url: "/ISBN-Validator",
                method: "POST",
                data: { isbn: $("#isbn").val() },
                success: function (response) {
                    if (response === "true") {
                        $("#isbn").removeClass("is-invalid").addClass("is-valid");
                        $("#isbnHelp").text("ISBN available").css({ "color": "green", "visibility": "visible" });
                    } else {
                        $("#isbn").addClass("is-invalid").removeClass("is-valid");
                        $("#isbnHelp").text("ISBN already exists").css({ "color": "red", "visibility": "visible" });
                    }
                },
                error: function (error) {
                    console.error("Error checking isbn:", error);
                    $("#isbn").addClass("is-invalid").removeClass("is-valid");
                    $("#isbnHelp").text("Error checking isbn. Please try again later.").css({ "color": "red", "visibility": "visible" });
                }
            });
            // const username = document.getElementById("isbn").value;
            // const xhr = new XMLHttpRequest();
            // xhr.open("POST", "ISBN-Validator", true);
            // xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            // xhr.onload = () => {
            //     if (xhr.status === 200) {
            //         document.getElementById("isbnHelp").value = xhr.responseText;
            //         if(xhr.responseText === 'true'){
            //             $("#isbn").removeClass("is-invalid").addClass("is-valid");
            //              $("#isbnHelp").text("ISBN available").css({ "color": "green", "visibility": "visible" });
            //         }
            //     } else {
            //         $("#isbn").addClass("is-invalid").removeClass("is-valid");
            //         $("#isbnHelp").text("Error checking isbn. Please try again later.").css({ "color": "red", "visibility": "visible" });
            //     }
            // };
            // xhr.send(`isbn=${encodeURIComponent(username)}`);
        }
    });

    $('#qty').on('blur', function () {
        validateQuantity();
    });

    $('#price').on('blur', function () {
        validatePrice();
    });

    $('#description').on('blur', function () {
        validateDescription();
    });

    // Validate on form submit
    $('#add-book-form').on('submit', function (event) {
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
