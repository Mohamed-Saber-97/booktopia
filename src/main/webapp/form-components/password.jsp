<%--
  Created by IntelliJ IDEA.
  User: Mohamed Saber
  Date: 9/14/2024
  Time: 3:53 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div class="form-group">
    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30 form-control" type="password" name="password"
           placeholder="Password" id="passwordInput" aria-describedby="passwordHelp">
    <small id="passwordHelp" class="form-text text-muted" style="visibility: hidden;">Password must be at least 6
        characters long.</small>
</div>

<div class="form-group">
    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30 form-control" type="password" name="confirmPassword"
           placeholder="Confirm Password" id="confirmPasswordInput" aria-describedby="confirmPasswordHelp">
    <small id="confirmPasswordHelp" class="form-text text-muted" style="visibility: hidden;">Passwords must
        match.</small>
</div>