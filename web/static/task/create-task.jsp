<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Create New Task</title>
    <style>
      * {
        box-sizing: border-box;
        margin: 0;
        padding: 0;
        font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
      }

      body {
        background: #f5f7fb;
        color: #333;
        line-height: 1.6;
        padding: 20px;
      }

      .container {
        max-width: 800px;
        margin: 0 auto;
        background: white;
        padding: 30px;
        border-radius: 12px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
      }

      .header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 30px;
        padding-bottom: 20px;
        border-bottom: 1px solid #eee;
      }

      h2 {
        color: #2c3e50;
        font-size: 24px;
        font-weight: 600;
      }

      .breadcrumb {
        color: #666;
        margin-bottom: 20px;
      }

      .breadcrumb a {
        color: #4a90e2;
        text-decoration: none;
      }

      .form-group {
        margin-bottom: 24px;
      }

      label {
        display: block;
        margin-bottom: 8px;
        color: #2c3e50;
        font-weight: 500;
      }

      input[type="text"],
      textarea,
      select,
      input[type="date"] {
        width: 100%;
        padding: 12px;
        border: 1px solid #ddd;
        border-radius: 6px;
        font-size: 14px;
        transition: border-color 0.3s;
      }

      input[type="text"]:focus,
      textarea:focus,
      select:focus,
      input[type="date"]:focus {
        border-color: #4a90e2;
        outline: none;
        box-shadow: 0 0 0 3px rgba(74, 144, 226, 0.1);
      }

      textarea {
        min-height: 120px;
        resize: vertical;
      }

      select[multiple] {
        min-height: 120px;
      }

      .button-group {
        display: flex;
        gap: 15px;
        margin-top: 30px;
      }

      .btn {
        padding: 12px 24px;
        border: none;
        border-radius: 6px;
        font-size: 14px;
        font-weight: 500;
        cursor: pointer;
        transition: all 0.3s;
        text-decoration: none;
        display: inline-flex;
        align-items: center;
        justify-content: center;
      }

      .btn-primary {
        background: #4a90e2;
        color: white;
      }

      .btn-primary:hover {
        background: #357abd;
      }

      .btn-secondary {
        background: #f5f7fb;
        color: #2c3e50;
        border: 1px solid #ddd;
      }

      .btn-secondary:hover {
        background: #e9ecef;
      }

      .required::after {
        content: "*";
        color: #dc3545;
        margin-left: 4px;
      }

      .error-message {
        color: #dc3545;
        background: #fff5f5;
        padding: 12px;
        border-radius: 6px;
        margin-bottom: 20px;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <div class="header">
        <h2>Create New Task</h2>
        <a
          href="${pageContext.request.contextPath}/project/view?id=${encodedProjectId}"
          class="btn btn-secondary"
          >Back to Project</a
        >
      </div>

      <div class="breadcrumb">
        <a href="${pageContext.request.contextPath}/home">Dashboard</a> &gt;
        <a
          href="${pageContext.request.contextPath}/project/view?id=${encodedProjectId}"
          >${project.projectName}</a
        >
        &gt; Create Task
      </div>

      <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
      </c:if>

      <form
        action="${pageContext.request.contextPath}/task/create"
        method="POST"
      >
        <input type="hidden" name="projectId" value="${encodedProjectId}" />

        <div class="form-group">
          <label class="required" for="taskName">Task Name</label>
          <input
            type="text"
            id="taskName"
            name="taskName"
            required
            placeholder="Enter task name"
          />
        </div>

        <div class="form-group">
          <label class="required" for="description">Description</label>
          <textarea
            id="description"
            name="description"
            required
            placeholder="Enter detailed task description"
          ></textarea>
        </div>

        <div class="form-group">
          <label class="required" for="assignedUsers">Assign To</label>
          <select id="assignedUsers" name="assignedUsers" multiple required>
            <c:forEach items="${projectMembers}" var="member">
              <option value="${member.userId}">
                ${member.firstName} ${member.lastName}
              </option>
            </c:forEach>
          </select>
        </div>

        <div class="form-group">
          <label class="required" for="dueDate">Due Date</label>
          <input type="date" id="dueDate" name="dueDate" required />
        </div>

        <div class="button-group">
          <button type="submit" class="btn btn-primary">Create Task</button>
          <a
            href="${pageContext.request.contextPath}/project/view?id=${encodedProjectId}"
            class="btn btn-secondary"
            >Cancel</a
          >
        </div>
      </form>
    </div>

    <script>
      // Set minimum date as today
      const today = new Date().toISOString().split("T")[0];
      document.getElementById("dueDate").setAttribute("min", today);

      function toggleMemberSelect() {
        const select = document.getElementById("assignedUsers");
        const icon = document.getElementById("expandCollapseIcon");
        if (select.style.display === "none") {
          select.style.display = "block";
          icon.textContent = "▲";
        } else {
          select.style.display = "none";
          icon.textContent = "▼";
        }
      }
    </script>
  </body>
</html>
