<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Task Details</title>
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
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }

            .task-info {
                margin-bottom: 20px;
                padding: 15px;
                border: 1px solid #ddd;
                border-radius: 4px;
            }

            .status-badge {
                display: inline-block;
                padding: 5px 10px;
                border-radius: 15px;
                font-size: 14px;
                font-weight: bold;
            }

            .status-pending { background-color: #ffeeba; }
            .status-in-progress { background-color: #b8daff; }
            .status-completed { background-color: #c3e6cb; }

            .due-soon { color: #856404; }
            .overdue { color: #721c24; }

            .btn {
                display: inline-block;
                padding: 8px 16px;
                margin: 5px;
                border: none;
                border-radius: 4px;
                background-color: #007bff;
                color: white;
                text-decoration: none;
                cursor: pointer;
            }

            .btn-danger {
                background-color: #dc3545;
            }

            .submission-section {
                margin-top: 20px;
                padding: 15px;
                border: 1px solid #ddd;
                border-radius: 4px;
            }

            .form-group {
                margin-bottom: 15px;
            }

            .form-group label {
                display: block;
                margin-bottom: 5px;
            }

            .form-group input {
                width: 100%;
                padding: 8px;
                border: 1px solid #ddd;
                border-radius: 4px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>${task.taskName}</h1>
            
            <div class="task-info">
                <p><strong>Project:</strong> ${task.projectName}</p>
                <p><strong>Description:</strong> ${task.description}</p>
                <p>
                    <strong>Status:</strong> 
                    <span class="status-badge status-${task.status.toLowerCase()}">${task.status}</span>
                </p>
                <p>
                    <strong>Due Date:</strong> 
                    <span class="${task.isDueSoon() ? 'due-soon' : ''} ${task.isOverdue() ? 'overdue' : ''}">
                        ${task.dueDate}
                        <c:if test="${task.isDueSoon()}"> (Due Soon)</c:if>
                        <c:if test="${task.isOverdue()}"> (Overdue)</c:if>
                    </span>
                </p>
            </div>

            <div class="assigned-members">
                <h3>Assigned Team Members</h3>
                <ul>
                    <c:forEach var="user" items="${task.assignedUsers}">
                        <li>${user}</li>
                    </c:forEach>
                </ul>
            </div>

            <%-- Hiển thị các nút chức năng dựa trên vai trò --%>
            <c:choose>
                <c:when test="${sessionScope.userRole == 'MANAGER'}">
                    <%-- Chức năng cho Project Manager --%>
                    <div>
                        <a href="update-task?id=${task.taskID}" class="btn">Edit Task</a>
                        <form action="delete-task" method="POST" style="display: inline;">
                            <input type="hidden" name="taskId" value="${task.taskID}">
                            <button type="submit" class="btn btn-danger" 
                                    onclick="return confirm('Are you sure you want to delete this task?')">
                                Delete Task
                            </button>
                        </form>
                    </div>
                </c:when>
                <c:when test="${sessionScope.userRole == 'MEMBER' && task.isAssignedToCurrentUser}">
                    <%-- Chức năng cho Team Member được assign --%>
                    <div class="submission-section">
                        <h3>Task Submission</h3>
                        <c:if test="${task.status != 'COMPLETED'}">
                            <form action="submit-task" method="POST" enctype="multipart/form-data">
                                <input type="hidden" name="taskId" value="${task.taskID}">
                                
                                <div class="form-group">
                                    <label for="submissionLink">Submission Link:</label>
                                    <input type="url" id="submissionLink" name="submissionLink" 
                                           value="${task.submissionLink}">
                                </div>
                                
                                <div class="form-group">
                                    <label for="submissionFile">Upload File:</label>
                                    <input type="file" id="submissionFile" name="submissionFile">
                                </div>

                                <div class="form-group">
                                    <label for="status">Update Status:</label>
                                    <select id="status" name="status">
                                        <option value="IN_PROGRESS" ${task.status == 'IN_PROGRESS' ? 'selected' : ''}>
                                            In Progress
                                        </option>
                                        <option value="COMPLETED" ${task.status == 'COMPLETED' ? 'selected' : ''}>
                                            Completed
                                        </option>
                                    </select>
                                </div>

                                <button type="submit" class="btn">Submit Task</button>
                            </form>
                        </c:if>
                        
                        <c:if test="${task.status == 'COMPLETED'}">
                            <p><strong>Submission Link:</strong> <a href="${task.submissionLink}" target="_blank">${task.submissionLink}</a></p>
                            <c:if test="${not empty task.submissionFilePath}">
                                <p><strong>Submitted File:</strong> <a href="download?taskId=${task.taskID}">Download</a></p>
                            </c:if>
                        </c:if>
                    </div>
                </c:when>
            </c:choose>

            <div class="task-assignments">
                <h3>Current Assignments</h3>
                <c:forEach var="assignment" items="${task.assignments}">
                    <div class="assignment">
                        <span>${assignment.fullName} (${assignment.username})</span>
                        <span>Joined: ${assignment.assignedAt}</span>
                    </div>
                </c:forEach>
                
                <c:if test="${sessionScope.userRole == 'MEMBER'}">
                    <form action="${pageContext.request.contextPath}/task/accept" method="POST">
                        <input type="hidden" name="taskId" value="${task.taskID}" />
                        <button type="submit" class="btn">Accept Task</button>
                    </form>
                </c:if>
            </div>

            <div style="margin-top: 20px">
              <a
                href="${pageContext.request.contextPath}/project/details?id=${task.projectID}"
                class="btn"
                >Back to Project</a
              >
            </div>
          </div>
        </body>
</html> 