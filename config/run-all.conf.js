var base = require('./base.conf');

module.exports = function (config) {
  base(config, 'foo.test_runner.run_all');
};
