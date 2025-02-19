<%-- 
    Document   : member-home
    Created on : Feb 19, 2025, 2:55:36 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Member Dashboard</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 20px;
                background-color: #f0f2f5;
            }
            
            .dashboard-container {
                max-width: 1200px;
                margin: 0 auto;
                background-color: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            
            .header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 20px;
            }
            
            .btn {
                background-color: #4267b2;
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                text-decoration: none;
            }
            
            .btn-danger {
                background-color: #dc3545;
            }
            
            .task-list {
                margin-top: 20px;
            }
            
            .task-item {
                border: 1px solid #ddd;
                padding: 15px;
                margin-bottom: 10px;
                border-radius: 4px;
            }
            
            .task-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 10px;
            }
            
            .task-status {
                padding: 5px 10px;
                border-radius: 15px;
                font-size: 14px;
            }
            
            .status-pending {
                background-color: #ffc107;
                color: #000;
            }
            
            .status-in-progress {
                background-color: #17a2b8;
                color: #fff;
            }
            
            .status-completed {
                background-color: #28a745;
                color: #fff;
            }
            
            .task-description {
                color: #666;
                margin-bottom: 10px;
            }
            
            .task-due-date {
                color: #dc3545;
                font-size: 14px;
            }
        </style>
    </head>
    <body>
        <div class="dashboard-container">
            <div class="header">
                <h1>Welcome, ${user.firstName} ${user.lastName}</h1>
                <div>
                    <a href="profile" class="btn">Profile</a>
                    <a href="logout" class="btn btn-danger">Logout</a>
                </div>
            </div>
            
            <h2>Your Tasks</h2>
            <div class="task-list">
                <c:forEach var="task" items="${assignedTasks}">
                    <div class="task-item">
                        <div class="task-header">
                            <h3>${task.taskName}</h3>
                            <span class="task-status status-${task.status.toLowerCase()}">${task.status}</span>
                        </div>
                        <div class="task-description">
                            ${task.description}
                        </div>
                        <div class="task-due-date">
                            Due: ${task.dueDate}
                        </div>
                        <div style="margin-top: 10px;">
                            <form action="update-task-status" method="POST" style="display: inline;">
                                <input type="hidden" name="taskId" value="${task.taskId}">
                                <select name="status" onchange="this.form.submit()">
                                    <option value="PENDING" ${task.status == 'PENDING' ? 'selected' : ''}>Pending</option>
                                    <option value="IN_PROGRESS" ${task.status == 'IN_PROGRESS' ? 'selected' : ''}>In Progress</option>
                                    <option value="COMPLETED" ${task.status == 'COMPLETED' ? 'selected' : ''}>Completed</option>
                                </select>
                            </form>
                            <a href="view-task?id=${task.taskId}" class="btn" style="margin-left: 10px;">View Details</a>
                        </div>
                    </div>
                </c:forEach>
                
                <c:if test="${empty assignedTasks}">
                    <p>No tasks assigned yet.</p>
                </c:if>
            </div>
        </div>
    </body>
</html>
