'use strict';

angular.module('namaaApaApp')
    .factory('Olive', function ($resource, DateUtils) {
        return $resource('api/olives/:id', {}, {
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
    });
