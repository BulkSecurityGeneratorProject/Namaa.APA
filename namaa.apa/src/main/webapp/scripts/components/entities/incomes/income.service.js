'use strict';

angular.module('namaaApaApp')

    .factory('Income', function ($resource, DateUtils, Principal) {
        return $resource('api/incomes', {}, {
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dateAdded = DateUtils.convertDateTimeFromServer(data.dateAdded);
                    return data;
                }
            },
            'update': {method: 'PUT'}
        });
    });
