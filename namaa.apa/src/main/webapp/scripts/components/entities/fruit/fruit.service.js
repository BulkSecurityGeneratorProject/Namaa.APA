'use strict';

angular.module('namaaApaApp')
    .factory('Fruit', function ($resource, DateUtils, Principal) {
        return $resource('api/fruits/:id', {}, {
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
