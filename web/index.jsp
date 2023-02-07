<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <title>Front End API Demo</title>
    <meta http-equiv="cont-type" content="application/xhtml+xml; charset=UTF-8"/>
    <script src="./scripts/jquery.js" type="text/javascript"></script>
    <script src="./scripts/jquery-scripts.js" type="text/javascript"></script>
    <script src="./scripts/JSON-to-table.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="./css/style.css">
</head>

<body>
<h1>Front End: REST</h1><br>
<button id="clear-table">Clear Tables</button>
<button onclick="window.location.href='/servlets.jsp'">Servlets Front End</button>
<br>
<br>
<fieldset>
    <legend>Show All Films</legend>
    <button id="show-data-json" class="button" name="json">Get All Films - JSON</button>
    <button id="show-data-xml" name="xml">Get All Films - XML</button>
    <button id="show-data-text" name="plaintext">Get All Films - Text</button>
    <br>
    <br>
    <div id="table"></div>
</fieldset>
<br>
<fieldset>
    <form id="search-form">
        <legend>Search Films</legend>
        <label>Search: </label><input type="text" name="search"/><br/>
        <br>
        <input type="button" name="json" value="Search - JSON" id="show-search-json"/>
        <input type="button" name="xml" value="Search - XML" id="show-search-xml"/>
        <input type="button" name="plaintext" value="Search - Text" id="show-search-text"/>
        <br>
        <br>
        <div id="search-table"></div>
    </form>
</fieldset>
<br>
<fieldset>
    <legend>Insert Film</legend>
    <form id="insert-form">
        <div id="insert-table"></div>
        <label>ID: </label><input type="number" name="id"/><br/>
        <label>Title: </label><input type="text" name="title"/><br/>
        <label>Year: </label><input type="number" name="year"/><br/>
        <label>Director: </label><input type="text" name="director"/><br/>
        <label>Stars: </label><input type="text" name="stars"/><br/>
        <label for="review-insert">Review:</label>
        <textarea id="review-insert" name="review"></textarea>
        <br>
        <br>
        <input type="button" name="insert" value="Insert Film" id="show-insert"/>
    </form>
</fieldset>
<br>
<fieldset>
    <legend>Update Film</legend>
    <form id="update-form">
        <div id="update-table"></div>
        <label>ID: </label><input type="number" name="id"/><br/>
        <label>Title: </label><input type="text" name="title"/><br/>
        <label>Year: </label><input type="number" name="year"/><br/>
        <label>Director: </label><input type="text" name="director"/><br/>
        <label>Stars: </label><input type="text" name="stars"/><br/>
        <label for="review-update">Review:</label>
        <textarea id="review-update" name="review"></textarea>
        <br>
        <br>
        <input type="button" name="update" value="Update Film" id="show-update"/>
    </form>
</fieldset>
<br>
<fieldset>
    <legend>Delete Film</legend>
    <form id="delete-form">
        <div id="delete-table"></div>
        <label>ID: </label><input type="number" name="id"/>
        <br>
        <br>
        <input type="button" name="delete" value="Delete Film" id="show-delete"/>
    </form>
</fieldset>
</body>
</html>
