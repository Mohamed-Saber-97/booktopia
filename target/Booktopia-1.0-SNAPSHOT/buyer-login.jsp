<%@include file="header.jsp" %>

<c:if test="${not empty error}">
   
</c:if>
<section class="bg0 p-t-104 p-b-116">
    <div class="container">
        <div class="flex-c-m flex-w">
            <div class="size-210 bor10 p-lr-70 p-t-55 p-b-70 p-lr-15-lg w-full-md">
                <form action="login" method="post" id="buyerLoginForm">
                    <h4 class="mtext-105 cl2 txt-center p-b-30">
                        Login
                    </h4>

                    <div class="bor8 m-b-20">
                        <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="email" name="email"
                               placeholder="Email Address" required>
                    </div>
                    <div class="bor8 m-b-20">
                        <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="password" name="password"
                               placeholder="Password" required>
                    </div>

                    <button class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer"
                            form="buyerLoginForm">
                        Login
                    </button>
                </form>
            </div>
        </div>
    </div>
</section>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        const form = document.querySelector('#buyerLoginForm');
        const fields = {
            email: form.querySelector('input[name="email"]'),
            password: form.querySelector('input[name="password"]')
        };

        for (const [key, input] of Object.entries(fields)) {
            input.addEventListener('blur', () => validateField(key));
        }

        form.addEventListener('submit', function (event) {
            let valid = true;

            // Clear previous error messages
            form.querySelectorAll('.error-message').forEach(el => el.remove());

            // Validate all fields on submit
            for (const key in fields) {
                if (!validateField(key)) {
                    valid = false;
                }
            }

            if (!valid) {
                event.preventDefault();
            }
        });

        function validateField(fieldName) {
            const input = fields[fieldName];
            let valid = true;
            let message = '';

            switch (fieldName) {
                case 'email':
                    if (!validateEmail(input.value)) {
                        if(!validateEmailbyServer(input.value)){
                            message = 'Please enter a valid email address.';
                            valid = false;
                        }else if(validateUniqueEmailbyServer(input.value)){
                            message = 'This email address is not registered.';
                            valid = false;
                        }
                    }
                    break;
                case 'password':
                    if (input.value.length < 6) {
                        message = 'Password must be at least 6 characters long.';
                        valid = false;
                    }
                    if (input.value.length >= 6 && !validatePass(input.value)) {
                        message = 'Password must contain at least one digit.';
                        valid = false;
                    }
                    break;
            }

            if (!valid) {
                showError(input, message);
            } else {
                clearError(input);
            }

            return valid;
        }

        function validateEmail(email) {
            const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            return emailPattern.test(email);
        }

        function validateEmailbyServer(email){
            const xhr = new XMLHttpRequest();
            xhr.open("POST", "validateEmail", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.onload = () => {
            if (xhr.status === 200) {
                if(xhr.responseText == 'true')
                    return true;
            } else {
                return false;
            }};
            xhr.send("email="+email);
        }

        function validateUniqueEmailbyServer(email){
            const xhr = new XMLHttpRequest();
            xhr.open("POST", "validateUniqueEmail", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.onload = () => {
            if (xhr.status === 200) {
                if(xhr.responseText == 'true')
                    return true;
            } else {
                return false;
            }};
            xhr.send("email="+email);
        }


        function validatePass(password) {
            const PassPattern = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/;
            return PassPattern.test(password);
        }

        
        function showError(input, message) {
            // Ensure previous error message is removed
            clearError(input);

            const error = document.createElement('div');
            error.className = 'error-message';
            error.style.color = 'red';
            error.style.marginTop = '5px';
            error.textContent = message;
            input.parentElement.appendChild(error);
        }

        function clearError(input) {
            const existingError = input.parentElement.querySelector('.error-message');
            if (existingError) {
                existingError.remove();
            }
        }
    });

</script>

<%@include file="footer.jsp" %>