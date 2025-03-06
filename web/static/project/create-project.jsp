<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Project</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f0f2f5;
                margin: 0;
                padding: 20px;
            }

            .container {
                max-width: 800px;
                margin: 0 auto;
                background: white;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }

            h2 {
                color: #1a73e8;
                margin-bottom: 30px;
                text-align: center;
            }

            .form-group {
                margin-bottom: 20px;
            }

            label {
                display: block;
                margin-bottom: 8px;
                color: #333;
                font-weight: 500;
            }

            input[type="text"],
            input[type="date"],
            textarea,
            select {
                width: 100%;
                padding: 12px;
                border: 1px solid #ddd;
                border-radius: 4px;
                box-sizing: border-box;
                font-size: 14px;
            }

            textarea {
                height: 120px;
                resize: vertical;
            }

            .member-selection {
                border: 1px solid #ddd;
                border-radius: 6px;
                padding: 20px;
                margin-bottom: 24px;
            }

            .search-box {
                margin-bottom: 15px;
                position: relative;
            }

            .search-box input {
                width: 100%;
                padding: 12px;
                padding-left: 35px;
                border: 1px solid #ddd;
                border-radius: 6px;
                font-size: 14px;
            }

            .search-box i {
                position: absolute;
                left: 12px;
                top: 50%;
                transform: translateY(-50%);
                color: #666;
            }

            .member-list {
                max-height: 300px;
                overflow-y: auto;
                display: grid;
                grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
                gap: 10px;
                margin-top: 10px;
            }

            .member-item {
                display: flex;
                align-items: center;
                gap: 8px;
                padding: 12px;
                border: 1px solid #eee;
                border-radius: 4px;
                transition: background-color 0.2s;
            }

            .member-item:hover {
                background-color: #f8f9fa;
            }

            .member-item input[type="checkbox"] {
                width: 16px;
                height: 16px;
                cursor: pointer;
            }

            .member-item label {
                margin: 0;
                cursor: pointer;
            }

            .selected-count {
                margin-top: 10px;
                font-size: 14px;
                color: #666;
            }

            /* Thêm style cho scrollbar */
            .member-list::-webkit-scrollbar {
                width: 8px;
            }

            .member-list::-webkit-scrollbar-track {
                background: #f1f1f1;
                border-radius: 4px;
            }

            .member-list::-webkit-scrollbar-thumb {
                background: #ccc;
                border-radius: 4px;
            }

            .member-list::-webkit-scrollbar-thumb:hover {
                background: #999;
            }

            .button-group {
                margin-top: 30px;
                display: flex;
                gap: 15px;
                justify-content: center;
            }

            .btn {
                padding: 12px 24px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 16px;
                font-weight: 500;
                transition: background-color 0.3s;
            }

            .btn-primary {
                background-color: #1a73e8;
                color: white;
            }

            .btn-primary:hover {
                background-color: #1557b0;
            }

            .btn-secondary {
                background-color: #f1f3f4;
                color: #333;
            }

            .btn-secondary:hover {
                background-color: #e8eaed;
            }

            .error-message {
                color: #d93025;
                margin-top: 20px;
                text-align: center;
            }

            /* Responsive Design */
            @media (max-width: 768px) {
                .container {
                    padding: 20px;
                }

                .button-group {
                    flex-direction: column;
                }

                .btn {
                    width: 100%;
                }
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Create New Project</h2>
            
            <form action="${pageContext.request.contextPath}/project/create" method="POST">
                <div class="form-group">
                    <label for="projectName">Project Name:</label>
                    <input type="text" id="projectName" name="projectName" required 
                           placeholder="Enter project name">
                </div>
                
                <div class="form-group">
                    <label for="description">Description:</label>
                    <textarea id="description" name="description" required 
                            placeholder="Enter project description"></textarea>
                </div>
                
                <div class="form-group">
                    <label for="startDate">Start Date:</label>
                    <input type="date" id="startDate" name="startDate" required>
                </div>
                
                <div class="form-group">
                    <label for="endDate">End Date:</label>
                    <input type="date" id="endDate" name="endDate" required>
                </div>
                
                <div class="member-selection">
                    <label>Team Members</label>
                    <div class="search-box">
                        <i class="fas fa-search"></i>
                        <input type="text" id="memberSearch" 
                               onkeyup="searchMembers()" 
                               placeholder="Search members...">
                    </div>
                    <div class="member-list" id="memberList">
                        <c:forEach items="${availableMembers}" var="member">
                            <div class="member-item">
                                <input type="checkbox" 
                                       id="member-${member.userID}" 
                                       name="teamMembers" 
                                       value="${member.userID}"
                                       class="member-checkbox">
                                <label for="member-${member.userID}">
                                    ${member.firstName} ${member.lastName}
                                </label>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="selected-count">
                        Selected members: <span id="selectedCount">0</span>
                    </div>
                </div>

                <div class="button-group">
                    <button type="submit" class="btn btn-primary">Create Project</button>
                    <a href="${pageContext.request.contextPath}/home" 
                       class="btn btn-secondary">Cancel</a>
                </div>
            </form>
            
            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>
        </div>

        <script>
            function searchMembers() {
                var input = document.getElementById('memberSearch');
                var filter = input.value.toLowerCase();
                var members = document.getElementById('memberList').children;

                for (var i = 0; i < members.length; i++) {
                    var name = members[i].textContent.toLowerCase();
                    if (name.indexOf(filter) > -1) {
                        members[i].style.display = "";
                    } else {
                        members[i].style.display = "none";
                    }
                }
            }

            // Set minimum date as today for start date
            const today = new Date().toISOString().split('T')[0];
            document.getElementById('startDate').setAttribute('min', today);
            
            // Update end date minimum when start date changes
            document.getElementById('startDate').addEventListener('change', function() {
                document.getElementById('endDate').setAttribute('min', this.value);
            });

            // Search functionality
            document.getElementById('memberSearch').addEventListener('input', function(e) {
                const searchTerm = e.target.value.toLowerCase();
                const memberItems = document.querySelectorAll('.member-item');

                memberItems.forEach(item => {
                    const memberName = item.querySelector('label').textContent.toLowerCase();
                    if (memberName.includes(searchTerm)) {
                        item.style.display = 'flex';
                    } else {
                        item.style.display = 'none';
                    }
                });
            });

            // Update selected count
            const memberCheckboxes = document.querySelectorAll('.member-checkbox');
            const selectedCountSpan = document.getElementById('selectedCount');

            function updateSelectedCount() {
                const selectedCount = document.querySelectorAll('.member-checkbox:checked').length;
                selectedCountSpan.textContent = selectedCount;
            }

            memberCheckboxes.forEach(checkbox => {
                checkbox.addEventListener('change', updateSelectedCount);
            });

            // Initial count
            updateSelectedCount();
        </script>

        <!-- Add Font Awesome for search icon -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    </body>
</html> 