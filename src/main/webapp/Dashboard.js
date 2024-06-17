function Ajax(method,url,data,callback){
	 var Request = new XMLHttpRequest();
       Request.open(method, url , true);
        Request.onreadystatechange = function () {
            if (Request.readyState == 4) {
                if (Request.status == 200) {
                    var data = Request.responseText;
                    callback(null,data)
                    
                    
                  
                    
                } else {
                    callback('error');
                }
            }
        };

        Request.setRequestHeader("Content-type", "application/json");
       Request.send(JSON.stringify(data));
}
var intervalId; 