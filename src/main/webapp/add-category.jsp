<%@include file="header.jsp"%>

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

<c:if test="${not empty error}">
    <div class="alert alert-danger text-center" style="width: fit-content; margin: 0 auto;">
        ${error}
    </div>
</c:if>
<section class="bg0 p-t-104 p-b-116">
    <div class="container">
        <div class="flex-w flex-tr">
            <div class="size-210 bor10 p-lr-70 p-t-55 p-b-70 p-lr-15-lg w-full-md" style="margin: 0 auto;">
                <form action="add-category" method="post">
                    <h4 class="mtext-105 cl2 txt-center p-b-30">
                        Add A New Category
                    </h4>
                    <div class="bor8 m-b-20">
                        <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" name="name"
                            placeholder="Name">
                    </div>
                    <button class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer">
                        Add Category
                    </button>
                </form>
            </div>
        </div>
    </div>
</section>

<%@include file="footer.jsp"%>