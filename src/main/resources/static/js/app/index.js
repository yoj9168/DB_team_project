function getCheckboxValue()  {
    // 선택된 목록 가져오기
    const query = 'input[name="search"]:checked';
    const selectedEls =
        document.querySelectorAll(query);

    // 선택된 목록에서 value 찾기
    let result = [];
    selectedEls.forEach((el) => {
        if(el.value != "name"){
            result.push(el.value);
        }
        else{
            result.push("fname");
            result.push("lname");
            result.push("minit");
        }
    });

    console.log(result);
    $.ajax({
        type: 'POST',
        url:'/employee',
        data: JSON.stringify(result),
        dataType:'json',
        contentType:'application/json; charset=utf-8',
    }).done(function(rs) {
        alert('POST 성공');
        console.log(JSON.stringify(rs));
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}