<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Task</title>
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

            .form-group input, .form-group textarea, .form-group select {
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

            .btn {
                background-color: #4267b2;
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }

            .error-message {
                color: red;
                margin-bottom: 10px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Update Task</h1>
            
            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/task/update" method="POST">
                <input type="hidden" name="taskId" value="${task.taskID}">
                
                <div class="form-group">
                    <label for="taskName">Task Name:</label>
                    <input type="text" id="taskName" name="taskName" value="${task.taskName}" required>
                </div>

                <div class="form-group">
                    <label for="description">Description:</label>
                    <textarea id="description" name="description" required>${task.description}</textarea>
                </div>

                <div class="form-group">
                    <label for="assignedTo">Assign To:</label>
                    <select id="assignedTo" name="assignedTo" required>
                        <c:forEach var="member" items="${teamMembers}">
                            <option value="${member.userID}" ${member.userID == task.assignedTo ? 'selected' : ''}>
                                ${member.firstName} ${member.lastName}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="dueDate">Due Date:</label>
                    <input type="date" id="dueDate" name="dueDate" value="${task.dueDate}" required>
                </div>

        <button type="submit" class="btn">Update Task</button>
        <a
          href="${pageContext.request.contextPath}/project/details?id=${task.projectID}"
          class="btn"
          style="margin-left: 10px"
          >Cancel</a
        >
      </form>
    </div>

        <script>
      // Set minimum date as today
      const today = new Date().toISOString().split("T")[0];
      document.getElementById("dueDate").setAttribute("min", today);
    </script>
  </body>
</html>
