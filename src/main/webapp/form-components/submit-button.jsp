<%--
  Created by IntelliJ IDEA.
  User: Mohamed Saber
  Date: 9/14/2024
  Time: 9:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div class="text-center mt-4">
    <button class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer"
            type="submit" id="submitBtn">
        Submit
    </button>
</div>

<%--<script>--%>
<%--    $(document).ready(function() {--%>
<%--        function validateField(field) {--%>
<%--            var isValid = true;--%>
<%--            var value = field.val().trim();--%>
<%--            var fieldId = field.attr('id');--%>
<%--            var helpText = $('#' + fieldId + 'Help');--%>

<%--            // Clear previous validation states--%>
<%--            field.removeClass('is-invalid is-valid');--%>
<%--            helpText.css('visibility', 'hidden');--%>

<%--            // Example validation logic (replace with actual validation rules)--%>
<%--            if (value === '') {--%>
<%--                field.addClass('is-invalid');--%>
<%--                helpText.text('This field is required.').css('visibility', 'visible');--%>
<%--                isValid = false;--%>
<%--            } else {--%>
<%--                field.addClass('is-valid');--%>
<%--            }--%>

<%--            return isValid;--%>
<%--        }--%>

<%--        function checkFormValidity() {--%>
<%--            var allFieldsValid = true;--%>

<%--            $('input, select').each(function() {--%>
<%--                var isValid = validateField($(this));--%>
<%--                if (!isValid) {--%>
<%--                    allFieldsValid = false;--%>
<%--                }--%>
<%--            });--%>

<%--            return allFieldsValid;--%>
<%--        }--%>

<%--        // Validate field on input change--%>
<%--        $('input, select').on('input change', function() {--%>
<%--            validateField($(this));--%>
<%--        });--%>

<%--        // Handle form submission--%>
<%--        $('#signupForm').on('submit', function(event) {--%>
<%--            var allFieldsValid = checkFormValidity();--%>

<%--            if (!allFieldsValid) {--%>
<%--                event.preventDefault(); // Prevent form submission--%>
<%--                // Trigger validation events for all fields--%>
<%--                $('input, select').each(function() {--%>
<%--                    validateField($(this)); // Ensure errors are shown--%>
<%--                });--%>
<%--            }--%>
<%--        });--%>

<%--        // Initial validation check should not mark fields as invalid--%>
<%--        $('input, select').each(function() {--%>
<%--            var field = $(this);--%>
<%--            if (field.val().trim() === '') {--%>
<%--                field.removeClass('is-invalid is-valid');--%>
<%--            }--%>
<%--        });--%>
<%--    });--%>
<%--</script>--%>