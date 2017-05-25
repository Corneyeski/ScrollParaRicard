(function() {
    'use strict';
    angular
        .module('pruebaScrollApp')
        .factory('Scroll', Scroll);

    Scroll.$inject = ['$resource'];

    function Scroll ($resource) {
        var resourceUrl =  'api/scrolls/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
