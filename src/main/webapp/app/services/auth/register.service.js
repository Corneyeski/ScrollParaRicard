(function () {
    'use strict';

    angular
        .module('pruebaScrollApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
