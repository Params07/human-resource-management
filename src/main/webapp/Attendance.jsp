<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div style="display:grid;">
        <div style="margin-left:50px;position: fixed; width: 100%; height:80px;	background-color:white; border-bottom: 1px solid lightgray;display:flex;;
        align-items:center;">
            <div style="margin-left:50px;">
            <input id="applyLeave" type="button" value="Apply Leave" onclick="ApplyLeaveToggle()">
            </div>
            <div style="text-align:right; width:65%;" >
                
                <input id="checked-in" type="button" value="checked in" onclick="checkedIn()">
                <input id="checked-out" style="display: none;" type="button" value="checked out" onclick="checkedout()">
                <span id="timer"></span>
            </div>
        </div>
         <div id="bodysection" style="margin-top:100px;margin-left:100px; display:flex; gap:50px;">
                <div id="LeaveForm" style="display:none;gap:20px;width: 300px; background:white;padding:15px;border-radius:6px;">
                          <div><h3>Leave form</h3></div>
                              <div class="input" >
                                   <label for = "from">From:</label>
         <input  id= "from" type="date" required >
        </div>
        <div class="input">
        <label for = "to">To:</label>
         <input  id= "to" type="date" required>
        </div>
        <div class="input">
            <label for = "reason">Reason:</label>
             <input  id= "reason" type="text" required>
            </div>
            <div class="input">
            
                <input  type="submit" value="Apply" onclick="ApplyLeave(event)">
               </div>
        </div>
        <div></div>
        
        <div style="background-color:white;height: 400px;width: 500px;display: grid;">
        <div style="display: flex; gap: 20px;font-size: 20px;font-weight: bolder;  box-shadow: 5px 5px 10px 0px rgba(0, 0, 0, 0.1);;
        position: fixed;height: 40px;align-items: center;width: 500px;justify-content: space-around; ">
            Leave requests
        </div>
       
       <div id ="leaveRequest" style="height: 100%; margin-top: 45px; width:450px;">
        
       </div>
    </div>
        
        </div>
     
     </div> </div>