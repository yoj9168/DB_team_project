function getCheckboxValue()  {
    var value_str = document.getElementById('searchRange');
    console.log(value_str.options[value_str.selectedIndex].value);
    // 선택된 목록 가져오기
    const query = 'input[name="search"]:checked';
    const selectedEls =
        document.querySelectorAll(query);
    const rangeValue = document.getElementById('searchRange').value
    const inputValue = document.querySelector('input[name="customInputField"]').value

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

    postAjax(result, rangeValue, inputValue);
}

function postAjax(_attribuite, _selectRange, _search)
{
    // Type은 List<String>, String, String 입니다.
    let testObj = {attribute : _attribuite, selectRange : _selectRange, search : _search}

    console.log(testObj);
    $.ajax({
        type: 'POST',
        url:'/employee',
        data: JSON.stringify(testObj),
        dataType:'json',
        contentType:'application/json; charset=utf-8'
    }).done(function(rs) {
        alert('POST 성공');
        console.log(replaceNullorZero(rs));
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}

function replaceNullorZero(_objs)
{
    console.log(_objs);
    const keys = Object.keys(_objs[0]);
    _objs.forEach((v) => {
        for(let i = 0; i < keys.length ; i++)
        {
            if(v[keys[i]] == null || v[keys[i]] == 0)
                v[keys[i]] = ""
        }
    })

    return _objs;
}