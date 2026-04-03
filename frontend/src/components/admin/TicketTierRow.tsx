import { TicketTier } from '../../types/ticketTier';

interface TicketTierRowProps {
  tier: TicketTier;
  onChange: (id: number, field: keyof TicketTier, value: string | number) => void;
  onDelete: (id: number) => void;
}

const TicketTierRow = ({ tier, onChange, onDelete }: TicketTierRowProps) => {
  return (
    <tr>
      <td className="px-6 py-4">
        <input
          className="w-full border-none bg-transparent p-0 text-sm font-semibold focus:ring-0 text-on-surface"
          type="text"
          value={tier.type}
          onChange={(e) => onChange(tier.id, 'type', e.target.value)}
        />
      </td>
      <td className="px-6 py-4 text-right">
        <input
          className="w-24 border-none bg-transparent p-0 text-sm font-semibold text-right focus:ring-0 text-on-surface"
          type="number"
          value={tier.unitPrice}
          onChange={(e) => onChange(tier.id, 'unitPrice', Number(e.target.value))}
        />
      </td>
      <td className="px-6 py-4 text-right">
        <input
          className="w-24 border-none bg-transparent p-0 text-sm font-semibold text-right focus:ring-0 text-on-surface"
          type="number"
          value={tier.inStock}
          onChange={(e) => onChange(tier.id, 'inStock', Number(e.target.value))}
        />
      </td>
      <td className="px-6 py-4 text-right">
        <input
          className="w-24 border-none bg-transparent p-0 text-sm font-semibold text-right focus:ring-0 text-on-surface"
          type="number"
          value={tier.orderLimit}
          onChange={(e) => onChange(tier.id, 'orderLimit', Number(e.target.value))}
        />
      </td>
      <td className="px-6 py-4 text-right">
        <button
          className="text-error/40 hover:text-error transition-colors"
          onClick={() => onDelete(tier.id)}
        >
          <span className="material-symbols-outlined text-lg">delete</span>
        </button>
      </td>
    </tr>
  );
};

export default TicketTierRow;
