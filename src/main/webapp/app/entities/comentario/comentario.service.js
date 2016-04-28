(function() {
    'use strict';
    angular
        .module('jhipsterApp')
        .factory('Comentario', Comentario);

    Comentario.$inject = ['$resource'];

    function Comentario ($resource) {
        var resourceUrl =  'api/comentarios/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
