<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="security"
uri="http://your.domain.com/security" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Update Project</title>
    <style>
      body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 20px;
        background-color: #f0f2f5;
      }

      .container {
        max-width: 800px;
        margin: 0 auto;
        background-color: white;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      }

      .form-group {
        margin-bottom: 15px;
      }

      .form-group label {
        display: block;
        margin-bottom: 5px;
        font-weight: bold;
      }

      .form-group input,
      .form-group textarea {
        width: 100%;
        padding: 8px;
        border: 1px solid #ddd;
        border-radius: 4px;
        box-sizing: border-box;
      }

      .form-group textarea {
        height: 100px;
        resize: vertical;
      }

      .member-selection {
        border: 1px solid #ddd;
        padding: 10px;
        border-radius: 4px;
        max-height: 200px;
        overflow-y: auto;
      }

      .member-item {
        margin: 5px 0;
      }

      .btn {
        background-color: #4267b2;
        color: white;
        padding: 10px 20px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        text-decoration: none;
        display: inline-block;
        margin-right: 10px;
      }

      .btn-danger {
        background-color: #dc3545;
      }

      .error-message {
        color: red;
        margin-bottom: 10px;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <h2>Update Project</h2>

      <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
      </c:if>

      <form
        action="${pageContext.request.contextPath}/project/update?id=${project.encodedProjectId}"
        method="POST"
        class="form"
      >
        <input type="hidden" name="projectId" value="${project.encodedProjectId}" />

        <div class="form-group">
          <label for="projectName">Project Name:</label>
          <input
            type="text"
            id="projectName"
            name="projectName"
            value="${project.projectName}"
            required
          />
        </div>

        <div class="form-group">
          <label for="description">Description:</label>
          <textarea id="description" name="description" required>
${project.description}</textarea
          >
        </div>

        <div class="form-group">
          <label for="startDate">Start Date:</label>
          <input
            type="date"
            id="startDate"
            name="startDate"
            value="${project.startDate}"
            required
          />
        </div>

        <div class="form-group">
          <label for="endDate">End Date:</label>
          <input
            type="date"
            id="endDate"
            name="endDate"
            value="${project.endDate}"
            required
          />
        </div>

        <div class="form-group">
          <label>Team Members:</label>
          <div class="member-list">
            <c:forEach var="member" items="${availableMembers}">
              <div class="member-item">
                <input type="checkbox" name="selectedMembers"
                value="${member.userID}"
                <c:forEach var="projectMember" items="${project.projectMembers}">
                  <c:if test="${projectMember.userId eq member.userID}">checked</c:if>
                </c:forEach>>
                <label>${member.firstName} ${member.lastName}</label>
              </div>
            </c:forEach>
          </div>
        </div>

        <div class="button-group">
          <button type="submit" class="btn">Update Project</button>
          <a
            href="${pageContext.request.contextPath}/project/view?id=${project.encodedProjectId}"
            class="btn btn-secondary"
            >Cancel</a
          >
        </div>
      </form>
    </div>

    <script>
      document.querySelector('form').addEventListener('submit', function(e) {
        var startDate = new Date(document.getElementById('startDate').value);
        var endDate = new Date(document.getElementById('endDate').value);
        var today = new Date();
        today.setHours(0, 0, 0, 0); // Reset time part for accurate date comparison
        
        if (startDate < today) {
          e.preventDefault();
          alert('Start date cannot be in the past');
          return;
        }
        
        if (endDate < startDate) {
          e.preventDefault();
          alert('End date cannot be earlier than start date');
          return;
        }
      });

      // Set min attribute for date inputs
      window.onload = function() {
        var today = new Date().toISOString().split('T')[0];
        document.getElementById('startDate').min = today;
        document.getElementById('endDate').min = today;
      }
    </script>
  </body>
</html>
