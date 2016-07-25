'use strict';

angular.module('namaaApaApp')
    .factory('GrainType2', function ($resource, DateUtils) {
        return $resource('api/grainType2s/:id', {}, {
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
