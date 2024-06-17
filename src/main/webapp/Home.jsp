<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<div style="margin-left:50px;">
  <div style="margin:0px;width:100%;height:40px;display:flex;justify-content:center;align-items:center;background-color:white;box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);">
     <div><h3>Attendance dashboard</h3></div>
  </div>
    <div style="margin:30px;width:400px;background-color:white;height:300px;">
        <div style="display: flex;gap: 20px;justify-content:center;padding:8px;">
            
            <div>
                <label>By date</label>
                <br>
                <input type="date" id="from" >
            </div>
           <div>
                <label for="team">team</label>
                <br>
                <select id="team"></select>
                
            </div>
            <div>
                <input type="button" id="applyFilter" onclick="applyFilter()" value="Apply">
            </div>
        </div>
    </div>
</div>