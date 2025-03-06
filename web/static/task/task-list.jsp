<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Task List</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 20px;
                background-color: #f0f2f5;
            }
            
            .container {
                max-width: 1200px;
                margin: 0 auto;
                background-color: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }
            
            .task-list {
                margin-top: 20px;
            }
            
            .task-item {
                border: 1px solid #ddd;
                padding: 15px;
                margin-bottom: 10px;
                border-radius: 4px;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            
            .task-info {
                flex-grow: 1;
            }
            
            .task-actions {
                display: flex;
                gap: 10px;
            }
            
            .btn {
                padding: 8px 16px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                text-decoration: none;
                display: inline-block;
            }
            
            .btn-primary {
                background-color: #4267B2;
                color: white;
            }
            
            .btn-danger {
                background-color: #dc3545;
                color: white;
            }
            
            .filters {
                margin-bottom: 20px;
                display: flex;
                gap: 20px;
                align-items: center;
            }
            
            .search-box {
                padding: 8px;
                border: 1px solid #ddd;
                border-radius: 4px;
                width: 200px;
            }
            
            select {
                padding: 8px;
                border: 1px solid #ddd;
                border-radius: 4px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Task List</h1>
            
            <div class="filters">
                <form action="${pageContext.request.contextPath}/task/list" method="GET">
                    <input type="hidden" name="projectId" value="${projectId}">
                    <input type="text" name="search" placeholder="Search by task name" 
                           value="${param.search}" class="search-box">
                    <select name="sort">
                        <option value="dueDate" ${param.sort == 'dueDate' ? 'selected' : ''}>Sort by Due Date</option>
                        <option value="status" ${param.sort == 'status' ? 'selected' : ''}>Sort by Status</option>
                    </select>
                    <button type="submit" class="btn btn-primary">Apply</button>
                </form>
            </div>

            <div class="task-list">
                <c:forEach var="task" items="${tasks}">
                    <div class="task-item">
                        <div class="task-info">
                            <h3>${task.taskName}</h3>
                            <p>Due Date: ${task.dueDate}</p>
                            <p>Status: ${task.status}</p>
                        </div>
                        <div class="task-actions">
                            <a href="view-task?id=${task.taskID}" class="btn btn-primary">View</a>
                            <c:if test="${sessionScope.userRole == 'MANAGER'}">
                                <a href="${pageContext.request.contextPath}/task/update?id=${task.taskID}" class="btn">Edit</a>
                                <button onclick="deleteTask('${task.taskID}')" class="btn btn-danger">Delete</button>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <a href="${pageContext.request.contextPath}/task/create" class="btn btn-primary">Create New Task</a>
        </div>

        <script>
            function deleteTask(taskID) {
                if (confirm('Are you sure you want to delete this task?')) {
                    fetch('${pageContext.request.contextPath}/task/delete', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        },
                        body: 'taskID=' + taskID
                    })
                    .then(response => {
                        if (response.ok) {
                            // Reload the page to show updated task list
                            window.location.reload();
                        } else {
                            alert('Failed to delete task');
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert('An error occurred while deleting the task');
                    });
                }
            }
        </script>
    </body>
</html>