//Sets button functions on page load
$(function () {

    //REST Page buttons
    $("#show-data-json").click(showAllFilms);
    $("#show-data-xml").click(showAllFilms);
    $("#show-data-text").click(showAllFilms);
    $("#show-search-json").click(showSearchFilms);
    $("#show-search-xml").click(showSearchFilms);
    $("#show-search-text").click(showSearchFilms);
    $("#show-insert").click(insertFilm);
    $("#show-update").click(updateFilm);
    $("#show-delete").click(deleteFilm);

    //Servlet Page buttons
    $("#show-data-json-servlet").click(showTable);
    $("#show-data-xml-servlet").click(showTable);
    $("#show-data-text-servlet").click(showTable);
    $("#show-search-json-servlet").click(showSearchTable);
    $("#show-search-xml-servlet").click(showSearchTable);
    $("#show-search-text-servlet").click(showSearchTable);
    $("#show-insert-servlet").click(showInsertServlet);
    $("#show-update-servlet").click(showUpdateServlet);
    $("#show-delete-servlet").click(showDeleteServlet);

    //Global button
    $("#clear-table").click(clearTable);
});

//Table header template string used in some functions
const tableHeader = "<table><thead id='tablehead'>" +
    "<tr>" +
    "<th>ID</th>" +
    "<th>TITLE</th>" +
    "<th>YEAR</th>" +
    "<th>DIRECTOR</th>" +
    "<th>STARS</th>" +
    "<th>REVIEW</th>" +
    "</tr></thead>" +
    "<tbody id='tablebody'>";

//HTML for loading icon GIF
const loadingGIF = "<img src=\"http://i.stack.imgur.com/FhHRx.gif\" alt=\"Loading...\">"

//REST FUNCTIONS//

function showAllFilms() {

    //Fetches format from the button pressed
    let format = $(this).attr('name');

    //GET request to return all films in the database
    $.ajax({
        type: 'GET',
        url: '/rest/films/' + format,
        datatype: format,
        //Displays loading gif while table builds
        ajaxStart: $('#table').html(loadingGIF),
        //Function returns data in chosen format and displays in a table
        success: determineDatatype('#table', format),
        error: function () {
            $('#table').html("");
                alert("An error occurred while processing " + format + " file")
        }
    });
}

function showSearchFilms() {

    //Fetches format from the button pressed
    let format = $(this).attr('name');
    //Fetches parameters from the form
    let params = $('#search-form').serialize();

    //GET request to return all films that match the search query
    $.ajax({
        type: 'GET',
        url: '/rest/films/' + format + '/' + params,
        datatype: format,
        //Displays loading gif while table builds
        ajaxStart: $('#search-table').html(loadingGIF),
        //Function returns data in chosen format and displays in a table
        success: determineDatatype('#search-table', format),
        error: function () {
            $('#search-table').html("");
                alert("Error: Search field cannot be empty")
        }
    });
}

function insertFilm() {

    //Serialize form and run POST request, displaying confirmation or error message based on result
    $.post("/rest/films/insert", $("#insert-form").serialize())
        .done(function (text) {
            $("#insert-table").html(text)
        })
        .fail(function () {
            alert("An error occurred while processing insert")
        });
}

//Sets up PUT function to replicate POST function
$.put = function (url, data, callback, type) {

    if ($.isFunction(data)) {
        type = type || callback;
        callback = data;
        data = {}
    }

    return $.ajax({
        url: url,
        type: 'PUT',
        success: callback,
        data: data,
        contentType: type
    });
};

function updateFilm() {

    //Serialize form and run PUT request, displaying confirmation or error message based on result
    $.put("/rest/films/update", $("#update-form").serialize())
        .done(function (text) {
            $("#update-table").html(text)
        })
        .fail(function (text) {
            $("#update-table").html(text);
                alert("An error occurred while processing update")
        });
}

//Sets up DELETE function to replicate POST function
$.delete = function (url, data, callback, type) {

    if ($.isFunction(data)) {
        type = type || callback;
        callback = data;
        data = {}
    }

    return $.ajax({
        url: url,
        type: 'DELETE',
        success: callback,
        data: data,
        contentType: type
    });
};

function deleteFilm() {

    //Serialize form and run DELETE request, displaying confirmation or error message based on result
    $.delete("/rest/films/delete", $("#delete-form").serialize())
        .done(function (text) {
            $("#delete-table").html(text);
        })
        .fail(function () {
            alert("An error occurred while processing deletion")
        });
}

//SERVLET FUNCTIONS//

function showTable() {

    //Fetches format from the button pressed
    let format = $(this).attr('name');

    //GET request to return all films in the database
    $.ajax({
        type: 'GET',
        url: '/servlets/getallfilms?format=' + format,
        datatype: format,
        //Displays loading gif while table builds
        ajaxStart: $('#table').html(loadingGIF),
        //Function returns data in chosen format and displays in a table
        success: determineDatatype('#table', format),
        error: function () {
            $('#table').html(""),
                alert("An error occurred while processing " + format + " file")
        }
    });
}

function showSearchTable() {

    //Fetches format from the button pressed
    let format = $(this).attr('name');
    //Fetches parameters from the form
    let params = $('#search-form').serialize();

    //GET request to return all films that match the search query
    $.ajax({
        type: 'GET',
        url: '/servlets/searchfilm?format=' + format + '&' + params,
        datatype: format,
        //Displays loading gif while table builds
        ajaxStart: $('#search-table').html(loadingGIF),
        //Function returns data in chosen format and displays in a table
        success: determineDatatype('#search-table', format),
        error: function () {
            $('#search-table').html(""),
                alert("An error occurred while processing " + format + " file")
        }
    });
}

function showInsertServlet() {

    //Fetches parameters from the form
    let params = $('#insert-form').serialize();

    $.ajax({
        type: 'GET',
        url: '/servlets/insertfilm?' + params,
        datatype: 'text',
        //Displays confirmation or error message based on whether the film was successfully inserted
        success: function (text) {
            $("#insert-table").html(text);
        },
        error: function () {
            alert("An error occurred while processing insert")
        }
    });
}

function showUpdateServlet() {

    //Fetches parameters from the form
    let params = $('#update-form').serialize();

    $.ajax({
        type: 'GET',
        url: '/servlets/updatefilm?' + params,
        datatype: 'text',
        //Displays confirmation or error message based on whether the film was successfully updated
        success: function (text) {
            $("#update-table").html(text);
        },
        error: function () {
            alert("An error occurred while processing update")
        }
    });
}

function showDeleteServlet() {

    //Fetches parameters from the form
    let params = $('#delete-form').serialize();

    $.ajax({
        type: 'GET',
        url: '/servlets/deletefilm?' + params,
        datatype: 'text',
        //Displays confirmation or error message based on whether the film was successfully deleted
        success: function (text) {
            $("#delete-table").html(text);
        },
        error: function () {
            alert("An error occurred while processing deletion")
        }
    });
}

//GLOBAL FUNCTIONS//

function clearTable() {
    $('#table').html("");
    $('#insert-table').html("");
    $('#delete-table').html("");
    $('#search-table').html("");
    $('#update-table').html("");
}

//Function creates a template table, parses the XML data and adds the data of each film into a HTML table
function parseXML(xml) {
    let tableString = tableHeader;
    $(xml).find('film').each(function () {
        tableString +=
            "<tr>" +
            "<td>" + $(this).find("id").text() + "</td>" +
            "<td>" + $(this).find("title").text() + "</td>" +
            "<td>" + $(this).find("year").text() + "</td>" +
            "<td>" + $(this).find("director").text() + "</td>" +
            "<td>" + $(this).find("stars").text() + "</td>" +
            "<td>" + $(this).find("review").text() + "</td>" +
            "</tr>";
    });
    tableString += "</tbody></table>";
    return tableString;
}

//Function creates a template table, parses plaintext data and adds the data of each film into a HTML table
function parsePlainText(data) {

    //Creates an array of each table element from the plaintext raw data delimited by '|', '[' and ']'
    const textArray = data.split(/[\[\]|]/g);
    //Filters empty strings from the array
    const cleanArray = textArray.filter(function (v) {
        return v !== ''
    });
    let tableString = tableHeader;
    let removeChars;

    //Iterate through each item in the array
    for (let i = 0; i < cleanArray.length; i++) {
        //Create a new row after every sixth item in the array
        if ((i % 6 === 0) && (i > 0) && (i !== cleanArray.length - 1)) {
            tableString += "</tr><tr>";
        }
        //Doesn't add a new row after the last item in the array
        if ((i % 6 === 0) && (i > 0) && (i === cleanArray.length - 1)) {
            tableString += "</tr>";
        }
        //Removes ', ' from the end of every sixth element, as each film is separated by these characters
        if (cleanArray[i].substring(cleanArray[i].length - 3) === "., ") {
            removeChars = cleanArray[i].substring(0, cleanArray[i].length - 2);
            //Adds amended data to the table
            tableString += "<td>" + removeChars + "</td>";
        } else {
            //Adds data to the table
            tableString += "<td>" + cleanArray[i] + "</td>";
        }
    }

    tableString += "</tbody><table>";
    return tableString;
}

//Function returns a different HTML table function based on the datatype requested
function determineDatatype(table, format) {

    let getData;

    if (format === 'json') {
        getData = function (json) {
            //createTable function found in 'JSON-to-table.js'
            //derived from https://www.jqueryscript.net/table/jQuery-JSON-To-Table-Plugin.html
            $(table).createTable(json);
        }
    } else if (format === 'xml') {
        getData = function (xml) {
            $(table).html(parseXML(xml));
        }
    } else if (format === 'plaintext') {
        getData = function (text) {
            $(table).html(parsePlainText(text));
        }
    }
    return getData;
}