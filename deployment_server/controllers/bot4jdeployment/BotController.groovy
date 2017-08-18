package bot4jdeployment

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class BotController {

    static allowedMethods = [save            : "POST",
                             update          : "PUT",
                             sendTrainingData: "POST",
                             delete          : "DELETE"]

    def transferLearningService
    def trainingDataService
    def botDeploymentService

    def deployBot(Bot bot) {
        botDeploymentService.deployBot(bot)
        redirect bot
    }


    def startTransferLearning(Bot bot) {
        transferLearningService.startTransferLearning(bot)
        redirect bot
    }


    def addFacebookSpec(Bot bot) {
        redirect controller: "facebookSpec", action: "create", params: [botId: bot.getId()]
    }

    def showFacebookSpec(Bot bot) {
        redirect controller: "facebookSpec", action: "show", params: [id: bot.getFacebookSpec().getId()]
    }

    def addSlackSpec(Bot bot) {
        redirect controller: "slackSpec", action: "create", params: [botId: bot.getId()]
    }

    def showSlackSpec(Bot bot) {
        redirect controller: "slackSpec", action: "show", params: [id: bot.getSlackSpec().getId()]
    }

    def addTelegramSpec(Bot bot) {
        redirect controller: "telegramSpec", action: "create", params: [botId: bot.getId()]
    }

    def showTelegramSpec(Bot bot) {
        redirect controller: "telegramSpec", action: "show", params: [id: bot.getTelegramSpec().getId()]
    }


    def uploadTrainingData(Bot bot) {
        respond bot
    }

    def sendTrainingData(TrainingDataCommand cmd) {
        println("SENDING TRAINING DATA")
        def bot = trainingDataService.postTrainingData(cmd)
        redirect bot
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Bot.list(params), model: [botCount: Bot.count()]
    }

    def show(Bot bot) {
        respond bot
    }

    def create() {
        respond new Bot(params)
    }

    @Transactional
    def save(Bot bot) {
        if (bot == null) {
            notFound()
            return
        }

        if (bot.hasErrors()) {
            respond bot.errors, view: 'create'
            return
        }

        bot.save flush: true

        if (bot == null) {
            notFound()
            return
        }

        if (bot.hasErrors()) {
            respond bot.errors, view: 'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'bot.label', default: 'Bot with ID'), bot.id])
                redirect bot
            }
            '*' { respond bot, [status: OK] }
        }
    }

    def edit(Bot bot) {
        respond bot
    }

    @Transactional
    def update(Bot bot) {
        if (bot == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (bot.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond bot.errors, view: 'edit'
            return
        }

        bot.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'bot.label', default: 'BotSendPayload'), bot.id])
                redirect bot
            }
            '*' { respond bot, [status: OK] }
        }
    }

    @Transactional
    def delete(Bot bot) {

        if (bot == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        bot = trainingDataService.deleteTrainingData(bot)

        String response = botDeploymentService.deleteBot(bot)

        bot.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'bot.label', default: 'BotSendPayload'), bot.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'bot.label', default: 'BotSendPayload'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
