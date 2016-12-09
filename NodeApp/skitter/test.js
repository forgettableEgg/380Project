var elastic = require('./elasticsearch');
elastic.indexExists().then(function (exists) {
  if (exists) {
    return elastic.deleteIndex();
  }
}).then(function () {
  return elastic.initIndex().then(elastic.initMapping).then(function () {
    //Add a few book titles for the autocomplete
    //elasticsearch offers a bulk functionality as well, but this is for a different time
    var promises = [
      'Test Skit1',
      'Test Skit2'
    ].map(function (skitName) {
      return elastic.addDocument({
        name: skitName,
        content: skitName + " Test TWEET WHOOP WHOOP",
        metadata: {
          skitLength: skitName.length,
          content: "Test Tweet Whoop Whoop"
        }
      });
    });
    var promises = [
      'Test Skit1',
      'Test Skit2'
    ].map(function (skitName) {
      return elastic.removeDocument({
        name: skitName,
        content: skitName
      });
    });
    return Promise.all(promises);
  });
});

//This should delete the two Tweets I just entered
