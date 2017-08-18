package bot4jdeployment

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TelegramSpecController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond TelegramSpec.list(params), model:[telegramSpecCount: TelegramSpec.count()]
    }

    def show(TelegramSpec telegramSpec) {
        respond telegramSpec
    }

    def create() {
        def telegramSpec = new TelegramSpec(params)
        def bot = Bot.get(params.long('botId'))
        telegramSpec.setBot(bot)
        respond telegramSpec
    }

    @Transactional
    def save(TelegramSpec telegramSpec) {
        if (telegramSpec == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (telegramSpec.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond telegramSpec.errors, view:'create'
            return
        }

        telegramSpec.save flush:true

        redirect controller:'bot', action:'show', params:[id:telegramSpec.bot.id]

    }

    def edit(TelegramSpec telegramSpec) {
        respond telegramSpec
    }

    @Transactional
    def update(TelegramSpec telegramSpec) {
        if (telegramSpec == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (telegramSpec.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond telegramSpec.errors, view:'edit'
            return
        }

        telegramSpec.save flush:true

        redirect controller:'bot', action:'show', params:[id:telegramSpec.bot.id]

    }

    @Transactional
    def delete(TelegramSpec telegramSpec) {

        if (telegramSpec == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        telegramSpec.delete flush:true

        redirect controller:'bot', action:'show', params:[id:telegramSpec.bot.id]

    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'telegramSpecPayload.label', default: 'TelegramSpecPayload'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
