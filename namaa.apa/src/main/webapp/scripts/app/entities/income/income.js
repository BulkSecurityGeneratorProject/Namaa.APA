'use strict';

angular.module('namaaApaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('income', {
                parent: 'entity',
                url: '/incomes',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'namaaApaApp.income.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/income/incomes.html',
                        controller: 'IncomeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('income');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })

       });
