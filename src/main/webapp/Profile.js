function Profile() {
    return new Promise(function(resolve, reject) {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4) {
                if (this.status == 200) {
                    document.getElementById('content').innerHTML = this.responseText;
                    resolve();
                } else {
                    reject("Error loading profile");
                }
            }
        };
        xhttp.open("GET", "Profile.jsp", true);
        xhttp.send();
    });
}

function showMangerProfile(id) {
    Ajax("post", "profileSet?user_id=" + id, "", function(error, data) {
        if (error) {
            console.error("Error fetching data:", error);
            return;
        }

        Profile().then(function() {
            toggleProfile();
        }).catch(function(error) {
            console.error("Error loading profile:", error);
        });
    });
}

function toggleProfile(){
	document.getElementById("managerProfile").style.display = 'block';
		document.getElementById("userProfile").style.display = 'none';

}