<%@include file="header.jsp" %>
<%@include file="notifications.jsp" %>

<style>
    /* Make the container a flexbox to center the content */
    .file-upload-container {
        display: flex;
        justify-content: center;
        align-items: center;
        /* height: 100vh; */
        /* Full viewport height */
    }

    /* Hide the default file input */
    .file-upload-input {
        opacity: 0;
        position: absolute;
        z-index: -1;
    }

    /* Style the custom label as a button */
    .custom-file-upload {
        display: inline-block;
        background-color: #222;
        color: white;
        padding: 12px 24px;
        font-size: 16px;
        cursor: pointer;
        border-radius: 8px;
        transition: background-color 0.3s ease;
        border: 2px solid transparent;
        text-align: center;
    }

    /* Icon inside the custom button */
    .custom-file-upload i {
        margin-right: 8px;
    }

    /* Change color on hover */
    .custom-file-upload:hover {
        background-color: #717fe0;
    }

    /* Wrapper styling (optional) */
    .file-upload-wrapper {
        text-align: center;
    }
</style>
<section class="bg0 p-t-104 p-b-116">
    <div class="container">
        <div class="flex-w flex-tr">
            <div class="size-210 bor10 p-lr-70 p-t-55 p-b-70 p-lr-15-lg w-full-md" style="margin: 0 auto;">
                <form action="edit-category" method="post" id="editCategoryForm">
                    <input type="hidden" name="id" value="${category.id}">
                    <h4 class="mtext-105 cl2 txt-center p-b-30">
                        Update Category
                    </h4>
                    <div class="form-group">
                        <input class="form-control" type="text" name="name" value="${category.name}" id="nameInput"
                               data-bs-toggle="popover" data-bs-trigger="manual">
                        <small id="nameHelp" class="form-text text-muted" style="visibility: hidden;">Name should not
                            exceed 100 characters.</small>
                    </div>
                    <button class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer">
                        Update Category
                    </button>
                </form>
            </div>
        </div>
    </div>
</section>
<script>
    $(document).ready(function () {
        $('#nameInput').on('blur', function () {
            let categoryName = $('#nameInput').val();
            let categoryId = $('input[name="id"]').val();

            // Proceed with AJAX if category name is not empty
            if (categoryName.trim() !== "") {
                $.ajax({
                    url: '/update-category-name', // URL of your servlet
                    method: 'POST',
                    data: {name: categoryName, id: categoryId},
                    success: function (response) {
                        if (response === "true") {
                            // Category name is valid
                            $('#nameInput').removeClass('is-invalid').addClass('is-valid');
                            $('#nameHelp').text("Category name is available").css({
                                "color": "green",
                                "visibility": "visible"
                            });
                        } else {
                            // Category name is invalid (either exists or too long)
                            $('#nameInput').addClass('is-invalid').removeClass('is-valid');
                            $('#nameHelp').text(response).css({
                                "color": "red",
                                "visibility": "visible"
                            });
                        }
                    },
                    error: function (error) {
                        console.error("Error checking category name:", error);
                        $('#nameInput').addClass('is-invalid').removeClass('is-valid');
                        $('#nameHelp').text("Error checking category name. Please try again later.").css({
                            "color": "red",
                            "visibility": "visible"
                        });
                        console.log(error.responseText); // Log error response for debugging
                    }
                });
            }
        });
        // Form submission handler
        $('#editCategoryForm').on('submit', function (event) {
            // Prevent form submission if the category name is invalid
            if ($('#nameInput').hasClass('is-invalid')) {
                event.preventDefault();
            }
        });
    });
</script>
<%@include file="footer.jsp" %>