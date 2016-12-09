var express = require('express');
var router = express.Router();

var elastic = require('../elasticsearch');

/* GET suggestions */
router.get('/suggest', function (req, res, next) {
    //console.log(req);
    elastic.getSuggestions(req.query.name).then(function (result) { res.json(result) });
});

/* POST document to be indexed */
router.post('/addDocument', function (req, res, next) {
    elastic.addDocument(req.body).then(function (result) { res.json(result) });
});

router.get('/searchDocuments', function(req, res, next) {
    console.log(req.query.name)
    elastic.searchDocuments(req.query.name).then(function (result) { res.json(result) });
});

router.post('/removeDocument', function(req, res, next) {
    elastic.removeDocument(req.body)
        .then(function  (result) { 
            res.json(result) 
        },function(error){
            console.log(error);
        });
});

router.get('/homePage', function(req, res) {
    res.sendfile(__dirname+'../public/AngularApp/app/homepage.html')
});
module.exports = router;
