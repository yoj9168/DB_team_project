function addInfo(){
    var fname = document.getElementById('fname').value;
    var minit = document.getElementById('minit').value;
    var lname = document.getElementById('lname').value;
    var ssn = document.getElementById('ssn').value;
    var bdate = document.getElementById('bdate').value;
    var address = document.getElementById('address').value;
    var sex = document.getElementById('sex');
    var sexValue = sex.options[sex.selectedIndex].text;
    var salary = document.getElementById('salary').value;
    var super_ssn = document.getElementById('super_ssn').value;
    var dno = document.getElementById('dno').value;
    if(ssn === ""){
        alert("SSN은 꼭 입력해주세요!");
    }
    else{
        var data = new Object();
        data.fname = fname;
        data.minit = minit;
        data.lname = lname;
        data.ssn = ssn;
        data.date = bdate;
        data.address = address;
        data.sex = sexValue;
        data.salary = salary;
        data.super_ssn = super_ssn;
        data.dno = dno;
        var ajaxData = JSON.stringify(data);
        $.ajax({
            type: 'POST',
            url:'/employee/insert',
            data:ajaxData,
            dataType:'json',
            contentType:'application/json; charset=utf-8',
            async : false
        }).done(function(rs) {
            alert("INSERT 완료!!");
            location.href='index'
        }).fail(function (error) {
            alert("입력 형식이 잘못 되었습니다!");
        });
    }
}