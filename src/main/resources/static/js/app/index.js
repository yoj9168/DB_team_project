$(document).ready(function (){
    $.ajax({
        type: 'POST',
        url:'/employee/department',
        dataType:'json',
        contentType:'application/json; charset=utf-8',
        async : false
    }).done(function(rs) {
        metadata["department"] = rs
        console.log(metadata);
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
    $.ajax({
        type: 'POST',
        url:'/employee/sex',
        dataType:'json',
        contentType:'application/json; charset=utf-8',
        async : false
    }).done(function(rs) {
        metadata["sex"] = rs
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
    $.ajax({
        type: 'POST',
        url:'/employee/name',
        dataType:'json',
        contentType:'application/json; charset=utf-8',
        async : false
    }).done(function(rs) {
        metadata["name"] = rs
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
})

function Dataframe(_tuples) {
    this.tuples = _tuples;
    this.csvData = Dataframe.prototype.tuplesToCSV(_tuples);
}

Dataframe.prototype.saveCSV = function (_filename) {
    let filename = _filename
    if (window.navigator && window.navigator.msSaveOrOpenBlob)
    {
        let blob = new Blob([decodeURIComponent(this.stringData)], {
            type: 'text/csv;charset=utf8'
        });
        window.navigator.msSaveOrOpenBlob(blob, filename);
    } else if (window.Blob && window.URL) {
        // HTML5 Blob
        let blob = new Blob([this.stringData], { type: 'text/csv;charset=utf8' });
        let csvUrl = URL.createObjectURL(blob);
        let a = document.createElement('a');
        a.setAttribute('style', 'display:none');
        a.setAttribute('href', csvUrl);
        a.setAttribute('download', filename);
        document.body.appendChild(a);

        a.click()
        a.remove();
    } else {
        // Data URI
        let csvData = 'data:application/csv;charset=utf-8,' + encodeURIComponent(this.stringData);
        let blob = new Blob([this.stringData], { type: 'text/csv;charset=utf8' });
        let csvUrl = URL.createObjectURL(blob);
        let a = document.createElement('a');
        a.setAttribute('style', 'display:none');
        a.setAttribute('target', '_blank');
        a.setAttribute('href', csvData);
        a.setAttribute('download', filename);
        document.body.appendChild(a);

        a.click()
        a.remove();
    }
}

Dataframe.prototype.tuplesToCSV = function(_tuples)
{
    if(_tuples.length === 0) return;

    const keys = Object.keys(_tuples[0]);
    let CSVString = "\uFEFF"; //BOM

    for(let i = 0; i < keys.length ; i++)
    {
        CSVString += keys[i] + ",";
    }
    CSVString += "\r\n"

    _tuples.forEach((v) => {
        for(let i = 0; i < keys.length ; i++)
        {
            let value = v[keys[i]];
            if(value.toString().includes(","))
                CSVString += "\"" + value + "\"" + ",";
            else
                CSVString += value + ",";
        }
        CSVString += "\r\n"
    })

    return CSVString;
}

Dataframe.prototype.showTable = function()
{
    this.removeTable();
    if(this.tuples.length === 0) return;

    let tbparent = document.getElementById('result');
    let allRows = this.tuples;
    let table = '<table id = "resultTable">';
    for (let singleRow = -1; singleRow < allRows.length; singleRow++) {
        if (singleRow === -1) {
            table += '<thead>';
            table += '<tr>';
        } else {
            table += '<tr id = "tuple'+ singleRow + '">';
        }

        let row = allRows[singleRow];
        let rowKeys = Object.keys(allRows[0]);
        for(let i = 0; i < rowKeys.length; i++){
            if(singleRow === -1){
                table += '<th>' + rowKeys[i] + '</th>';
            } else {
                table += '<td>' + row[rowKeys[i]] + '</td>';
            }
        }
        if (singleRow === -1) {
            table += '</tr>';
            table += '</thead>';
            table += '<tbody>';
        } else {
            table += '</tr>';
        }
    }
    table += '</tbody>';
    table += '</table>';

    tbparent.insertAdjacentHTML("afterbegin", table);
    let elemRow;

    for (let singleRow = 0; singleRow < allRows.length; singleRow++) {
        elemRow = document.getElementById('tuple'+singleRow);
        elemRow.addEventListener("click", (e) => {rowClickEventListner(e, singleRow)});
    }
}

Dataframe.prototype.removeTable = function()
{
    let tbparent = document.getElementById('result');
    let tb = document.getElementById('resultTable');
    if(tb != null)
        tbparent.removeChild(tb);
}

var dataframe;
var selectedRows = [];
var rowLength = 0;
var metadata = {};

function getCheckboxValue()  {
    var value_str = document.getElementById('searchRange');
    console.log(value_str.options[value_str.selectedIndex].value);
    // 선택된 목록 가져오기
    const query = 'input[name="search"]:checked';
    const selectedEls =
        document.querySelectorAll(query);
    const rangeValue = document.getElementById('searchRange').value
    const inputField = document.getElementById('actualInput');
    let inputValue;
    if(inputField)
        inputValue = preprocessInput(rangeValue, inputField.value);

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
        console.log(rs);
        dataframe = new Dataframe((replaceNullorZero(rs)));
        dataframe.showTable();
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });

    $.ajax({
        type: 'POST',
        url:'/employee/count',
        data: JSON.stringify(testObj),
        dataType:'json',
        contentType:'application/json; charset=utf-8'
    }).done(function(rs) {
        let x = document.getElementsByClassName("countTuple")[0];
        x.innerText=rs;
        console.log(rs);
        // 행 선택 기능을 위한 초기화 파트
        rowLength = rs;
        selectedRows = [];
        for(let i = 0; i < rowLength; i++)
            selectedRows.push(false);
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}

function replaceNullorZero(_objs)
{
    if(_objs.length === 0) return [];
    const keys = Object.keys(_objs[0]);
    _objs.forEach((v) => {
        for(let i = 0; i < keys.length ; i++)
        {
            if(v[keys[i]] == null || v[keys[i]] === 0 || v[keys[i]] === "nullnullnull")
                v[keys[i]] = "";
        }
    })

    return _objs;
}

function rowClickEventListner(event, index)
{
    elemRow = document.getElementById('tuple'+index);
    console.log(document.getElementById('tuple'+index));
    if(selectedRows[index] = !selectedRows[index])
        elemRow.style.cssText = 'background-color: #d4f4d4';
    else
        elemRow.style.cssText = 'background-color: #ffffff';
}

function postSelectedRows()
{
    let rows = []
    for(let i = 0; i < rowLength; i++)
        if(selectedRows[i])
            rows.push(dataframe.tuples[i])
    console.log(rows)
}

function makeDropdownFromMetadata(_value)
{
    if(_value === undefined) return;

    let inputField = document.getElementById("customInputField");
    let htmlString = "";
    let metaField;

    switch(_value)
    {
        case "dname" :	metaField = "department"; break;
        case "ssn" :	metaField = "name"; break;
        case "sex" :	metaField = "sex"; break;
        default : 	metaField = undefined; break;
    }

    if(metaField === undefined)
    {
        htmlString += "<input id = \"actualInput\" /input>";
    }
    else
    {
        htmlString += "<select id = \"actualInput\">";
        for(let i = 0; i < metadata[metaField].length ; i++)
        {
            htmlString += "<option value = \"" + metadata[metaField][i] + "\">" + metadata[metaField][i] + "</option>";
        }
    }

    inputField.innerHTML = htmlString;
}

function preprocessInput(_range, _value)
{
    let modified = _value.toString();
    switch(_range)
    {
        case "bdate" :
            if(_value < 10)
                modified = "0" + modified;
            break;
    }

    return modified;
}