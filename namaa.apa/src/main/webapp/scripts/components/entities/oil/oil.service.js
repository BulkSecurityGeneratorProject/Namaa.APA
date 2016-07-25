'use strict';

angular.module('namaaApaApp')
    .factory('Oil', function ($resource, DateUtils) {
        return $resource('api/oils/:id', {}, {
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
